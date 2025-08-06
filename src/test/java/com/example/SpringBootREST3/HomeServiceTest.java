package com.example.SpringBootREST3;

import com.example.SpringBootREST3.service.HomeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith({SpringExtension.class})
class HomeServiceTest {

    @InjectMocks
    @Spy
    private HomeService homeService;

    @BeforeEach
    public void init() {
        //set private field / variables
        ReflectionTestUtils.setField(homeService, "value123", "123");
    }

    @Test
    void testValidateTestValue() {
        Assertions.assertEquals(300, homeService.validateTestValue());
        ReflectionTestUtils.setField(homeService, "value123", "200");
        Assertions.assertEquals(200, homeService.validateTestValue());
        ReflectionTestUtils.setField(homeService, "value123", "hdhdhdhd");
        Assertions.assertEquals(300, homeService.validateTestValue());
    }

    /*
        @Test
            void testPriceDataWithInvalidPrimaryKeyAndHigherContentVersion() {
                var pushMessageDetails = createPushMessage();
                var businessUnit = testUtil.createDefaultBusinessDetail();
                var currencies = testUtil.createDefaultCurrencyList();
                pushMessageDetails.setData(VALID_PROTO_PRICE_DATA);
                SPERuntimeException speRuntimeException = new SPERuntimeException(DATABASE_ERROR_UNIQUE_CONSTRAINT);
                doThrow(speRuntimeException).when(retailItemSalesPriceRepository).deletePriceByKey(any(), any(), any(), any(), any());
                List<String> databaseContentVersion = Collections.singletonList("1");
                eventsHelper.setIncomingContentVersion(100L);
                doReturn(databaseContentVersion).when(retailItemSalesPriceRepository).getPriceContentVersion(any(), any(), any(), any(), any());
                assertThrows(SPERuntimeException.class, () -> priceConsumerService.processPriceData(pushMessageDetails, businessUnit, currencies, eventsHelper));
            }
        
            @Test
            void testPriceDataWithInvalidPrimaryKeyAndNotHigherContentVersion() {
                var pushMessageDetails = createPushMessage();
                var businessUnit = testUtil.createDefaultBusinessDetail();
                var currencies = testUtil.createDefaultCurrencyList();
                pushMessageDetails.setData(VALID_PROTO_PRICE_DATA);
                SPERuntimeException speRuntimeException = new SPERuntimeException(DATABASE_ERROR_UNIQUE_CONSTRAINT);
                doThrow(speRuntimeException).when(retailItemSalesPriceRepository).deletePriceByKey(any(), any(), any(), any(), any());
                List<String> databaseContentVersion = Collections.singletonList("100");
                eventsHelper.setIncomingContentVersion(1L);
                doReturn(databaseContentVersion).when(retailItemSalesPriceRepository).getPriceContentVersion(any(), any(), any(), any(), any());
                assertFalse(priceConsumerService.processPriceData(pushMessageDetails, businessUnit, currencies, eventsHelper));
            }
        
            @Test
            void testPriceDataWithDatabaseError() {
                var pushMessageDetails = createPushMessage();
                var businessUnit = testUtil.createDefaultBusinessDetail();
                var currencies = testUtil.createDefaultCurrencyList();
                pushMessageDetails.setData(VALID_PROTO_PRICE_DATA);
                SPERuntimeException speRuntimeException = new SPERuntimeException(DATABASE_ERROR_LOG_MESSAGE);
                doThrow(speRuntimeException).when(retailItemSalesPriceRepository).deletePriceByKey(any(), any(), any(), any(), any());
                assertThrows(SPERuntimeException.class, () -> priceConsumerService.processPriceData(pushMessageDetails, businessUnit, currencies, eventsHelper));
            }
        	
        	
        	@ExtendWith(MockitoExtension.class)
        @MockitoSettings(strictness = Strictness.LENIENT)
        public class CommonServiceTest {
            private CommonService commonService;
            private PriceConsumerService priceConsumerService;
            private ValidationHelper validationHelper;
            private CommonHelper commonHelper;
            private ObjectMapper objectMapper;
            private EventsHelper eventsHelper;
            private RedisTemplate<String, String> redisTemplate;
            private ERIXPriceTestUtil testUtil;
            @Mock
            private ItemRawPriceRepository itemRawPriceRepository;
            @Mock
            private CommonRepository commonRepository;
            @Mock
            private RetailItemSalesPriceRepository retailItemSalesPriceRepository;
            @Mock
            private RedisCacheHelper redisCacheHelper;
        
            @BeforeEach
            public void init() {
                objectMapper = new ObjectMapper();
                commonHelper = new CommonHelper(objectMapper);
                validationHelper = new ValidationHelper(commonHelper);
                priceConsumerService = new PriceConsumerService(commonHelper, validationHelper, itemRawPriceRepository,
                        retailItemSalesPriceRepository, false);
                testUtil = new ERIXPriceTestUtil();
                eventsHelper = new EventsHelper();
                redisTemplate = new RedisTemplate<>();
                commonService = new CommonService(commonRepository, priceConsumerService, null, redisTemplate,
                        validationHelper, commonHelper, redisCacheHelper, 10);
            }
        
            @Test
            public void testPriceForDBSuccessAndRedisIssue() {
                var pushMessageDetails = createPushMessage();
                var businessUnit = testUtil.createDefaultBusinessDetail();
                var currencies = testUtil.createCurrencyList();
                pushMessageDetails.setData(VALID_PROTO_PRICE_DATA);
                doReturn(currencies).when(commonRepository).getCurrencyList(any());
                List<String> buCodes = new ArrayList<>();
                SPERuntimeException speRuntimeException = new SPERuntimeException(REDIS_DELETE_FAIL_LOG_MESSAGE);
                doThrow(speRuntimeException).when(redisCacheHelper).clearRedisCacheAcrossRegions(any());
                assertThrows(SPERuntimeException.class, () -> commonService.processData(pushMessageDetails, ERixPriceConstants.ERixFlow.PRICE, businessUnit, currencies, buCodes, eventsHelper));
            }
        
            @Test
            void testPriceWithSameContentVersion() {
                var pushMessageDetails = createPushMessage();
                var businessUnit = testUtil.createDefaultBusinessDetail();
                var currencies = testUtil.createCurrencyList();
                currencies.getFirst().setCurrencyCode("AED");
                pushMessageDetails.setData(VALID_PRICE_WITH_VALID_TO_DATA);
                pushMessageDetails.setPublishTime(LocalDateTime.now().toString());
                doNothing().when(retailItemSalesPriceRepository).deletePriceByKey(any(), any(), any(), any(), any());
                List<String> databaseContentVersion = Collections.singletonList("1616499899861");
                doReturn(databaseContentVersion).when(retailItemSalesPriceRepository).getPriceContentVersion(any(), any(), any(), any(), any());
                doNothing().when(redisCacheHelper).clearRedisCacheAcrossRegions(any());
                List<String> buCodes = new ArrayList<>();
                commonService.processData(pushMessageDetails, ERixPriceConstants.ERixFlow.PRICE, businessUnit, currencies, buCodes, eventsHelper);
            }
        
            private PushMessageDetails createPushMessage() {
                var pushMessageDetails = new PushMessageDetails();
                pushMessageDetails.setMessageId("123445");
                pushMessageDetails.setAttributes(testUtil.createMessageAttributes("80347101", "ART", "GR", "RU",
                        "AE", "1616499899861"));
                return pushMessageDetails;
            }
        }


    */


}

