package com.example.SpringBootREST3.controller;

import com.example.SpringBootREST3.aspect.Logging;
import com.example.SpringBootREST3.entity.Movie;
import com.example.SpringBootREST3.entity.MovieNew;
import com.example.SpringBootREST3.exception.ErrorProperties;
import com.example.SpringBootREST3.exception.ErrorResponse;
import com.example.SpringBootREST3.exception.OrderException;
import com.example.SpringBootREST3.model.AuthenticationResponse;
import com.example.SpringBootREST3.model.MovieModel;
import com.example.SpringBootREST3.model.OrderRequestForm;
import com.example.SpringBootREST3.model.OrderResponse;
import com.example.SpringBootREST3.model.Person;
import com.example.SpringBootREST3.service.HomeService;
import com.example.SpringBootREST3.service.MovieService;
import com.example.SpringBootREST3.service.UserService;
import com.example.SpringBootREST3.util.JWTTokenUtil;
import com.example.SpringBootREST3.util.OrderServiceUtil;
import io.jaegertracing.internal.JaegerTracer;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.opentracing.Span;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/v3/rest")
@Tag(name = "someTag", description = "someDescription", externalDocs = @ExternalDocumentation(description = "Google", url = "https://www.google.com/"))
public class NavController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private JaegerTracer jaegerTracer;

    @Autowired
    private ObservationRegistry observationRegistry;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private OrderServiceUtil orderServiceUtil;

    @Autowired
    private ErrorProperties errorProperties;

    Logger log = LoggerFactory.getLogger(NavController.class);

    //Secured
    // http://localhost:9100/v3/rest/getMoviesOfDirector/Satyajit Ray
    // Authorization
    // Type     -> Bearer Token
    // Token    -> eyJhbGciOiJIUzUxMiJ9...
    // JWT Token is in the form of Bearer Token
    @GetMapping(value = "/getMoviesOfDirector/{director}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getMoviesOfDirector(
            @PathVariable("director") String director) {
        Span span = jaegerTracer.buildSpan("getMoviesOfDirector").start();
        log.info("Inside getMoviesOfDirector controller... ");

        //Counter metric
        Counter counter = Counter.builder("getMoviesOfDirector")
                .description("a number of requests to /getMoviesOfDirector endpoint")
                .register(meterRegistry);
        counter.increment();

        //Timer metric
        Timer.Sample timer = Timer.start(meterRegistry);
        List<Movie> movies = movieService.getMoviesOfDirector(director);
        timer.stop(Timer.builder("getMoviesOfDirector_Timer").register(meterRegistry));

        //Observation API
        Observation.createNotStarted("getMoviesOfDirector_Count", observationRegistry)
                .lowCardinalityKeyValue("request-uid", String.valueOf(new Random().nextInt(100)))
                .observe(() -> {
                    List<Movie> movies2 = movieService.getMoviesOfDirector(director);
                    log.debug("Counting getMoviesOfDirector");
                });

        return ResponseEntity.ok(movies);
    }

    //http://localhost:9100/v3/rest/authenticate
    /*
        {
        "userId":"eyabhikg",
        "password":"12345"
        }
     */
    //var jsonData = JSON.parse(responseBody);
    //pm.environment.set("SB3_BEARER_TOKEN", jsonData['jwt']);
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody Map<String, Object> claims){

        try {
            log.info("In authenticate method...");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(claims.get("userId"), claims.get("password")));
        } catch (Exception e) {
            log.error("Authentication Error.......");
            throw new OrderException("101", "Invalid username or password", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = userService.loadUserByUsername(String.valueOf(claims.get("userId")));
        String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AuthenticationResponse(userDetails.getUsername(), userDetails.getAuthorities().stream().map(String::valueOf).toList().getFirst(), jwt))
                .getBody();

    }



    // http://localhost:9100/v3/rest/getMoviesByDirectorAndGenre/Satyajit Ray/Comedy
    @GetMapping(value = "/getMoviesByDirectorAndGenre/{director}/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> getMoviesByDirectorAndGenre(
            @PathVariable("director") String director,
            @PathVariable("genre") String genre) {

        //Counter metric
        Counter counter = Counter.builder("getMoviesByDirectorAndGenre")
                .description("a number of requests to /getMoviesByDirectorAndGenre endpoint")
                .register(meterRegistry);
        counter.increment();

        //Gauge metric
        Gauge.builder("users_count", movieService::countUsers)
                .description("A current number of users in the system")
                .register(meterRegistry);

        //Timer metric
        Timer.Sample timer = Timer.start(meterRegistry);

        List<Movie> movies = movieService.findByDirectorAndGenre(director, genre);

        timer.stop(Timer.builder("getMoviesByDirectorAndGenre_Timer").register(meterRegistry));
        return ResponseEntity.ok(movies);
    }

    //http://localhost:9100/v3/rest/hello
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Logging
    public ResponseEntity<String> hello() {

        Span span = jaegerTracer.buildSpan("helloSpan").start();
        log.info("Inside hello controller... ");

        //Counter metric
        Counter counter = Counter.builder("getMoviesOfDirector")
                .description("a number of requests to /getMoviesOfDirector endpoint")
                .register(meterRegistry);
        counter.increment();

        span.finish();

        return ResponseEntity.status(HttpStatus.OK)
                .body("Hello World");
    }

    @GetMapping(value = "/hello2", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> hello2() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Hello World 2");
    }

    @PostMapping(value = "/helloPost", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<String> helloPost(@Valid @RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(person.getFirstName().concat("----").concat(person.getLastName()));
    }

    @GetMapping("/home")
    public String getResponse(){
        return homeService.getResponse();
    }

    // http://localhost:9100/v3/rest/addMovie
    @PostMapping(value = "/addMovie", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MovieNew addMovie(@Valid @RequestBody MovieModel movieModel){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDeepCopyEnabled(Boolean.TRUE);
        modelMapper.getConfiguration().setAmbiguityIgnored(Boolean.TRUE);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MovieNew movieNew = modelMapper.map(movieModel, MovieNew.class);
        return movieService.addMovie(movieNew);

    }

    //http://localhost:9100/v3/rest/getOrder3/Feb/red?parmRequestSource=web&parmAudienceType=external
    //Header - Actor = Microservice
    /*
        {
            "orderId": 100,
            "item": "iPhone",
            "itemType": "Phone",
            "invoiceType": 2
        }
     */
    @PostMapping(value = "/getOrder3/{month}/{colour}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getOrder3(
            @PathVariable(value = "month") String month,
            @PathVariable(value = "colour") String colour,
            @RequestParam(value = "parmRequestSource") String parmRequestSource,
            @RequestParam(required = false, value = "parmAudienceType") String parmAudienceType,
            @RequestHeader(value = "Actor") String actor,
            @RequestBody OrderRequestForm orderRequestForm) {

        OrderResponse orderResponse = new OrderResponse();



            String item = orderRequestForm.getItem();
            String itemType = orderRequestForm.getItemType();
            Integer invoiceType = orderRequestForm.getInvoiceType();

            if(!orderServiceUtil.allowedItems.contains(item)) {
                errorProperties.throwSPEException(ErrorProperties.ErrorCode.INVALID_ITEM, List.of(item));
            }
            else {
                orderResponse.setOrderId(orderRequestForm.getOrderId());
                orderResponse.setItem(orderRequestForm.getItem());
                orderResponse.setItemType(orderRequestForm.getItemType());
                orderResponse.setInvoiceType(orderRequestForm.getInvoiceType());
                orderResponse.setParmRequestSource(parmRequestSource);
                orderResponse.setParmAudienceType(parmAudienceType);
                orderResponse.setMonth(month);
                orderResponse.setActor(actor);
            }


        return ResponseEntity.status(HttpStatus.OK)
                .header("test", "test")
                .body(orderResponse);

    }
}
