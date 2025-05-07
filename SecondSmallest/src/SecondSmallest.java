import java.util.Arrays;

public class SecondSmallest {
        /**
         * @param input
         * @return secondElement
         */
        public  int secondSmallest(int[] input) {
            final String METHOD_NAME = "secondSmallest2";
            System.out.println("Method Invoked:" + METHOD_NAME + ":" + Arrays.toString(input));
            if (input == null || input.length == 0) {
                throw new NullPointerException("Array reference is null/empty please check once");

            }
            int secondElement = Arrays.stream(input).sorted().distinct().skip(1).findAny().getAsInt();
            System.out.println("Method Response" + METHOD_NAME + ":" + secondElement);
            ;
            return secondElement;

        }

}
