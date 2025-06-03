package controller;

import org.example.controller.NumberController;
import org.example.service.NumberService;

@ExtendWith(MockitoExtension.class)
public class NumberControllerTest {

    @InjectMocks
    private NumberController numberController;

    @Mock
    private NumberService numberService;

    @Test
    void testCheckEvenOdd(){
        int num = 4;
        Mockito.when(numberService.checkOddOrEven(num)).thenReturn("Even");
        String result = numberController.check(num);
        assertEquals("Even",result);
        Mockito.verify(numberService).checkOddEven(num);
    }
}
