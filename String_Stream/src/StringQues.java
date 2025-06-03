import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringQues {
    List<String> stringList = Arrays.asList("Apple","Mango","Orange","apple","","orange","Java","java","Mango","","Blueberry");

    public void countStrings(){
        //1.Count the number of Strings
        long count = stringList.stream().count();
        System.out.println("String count : "+count);

        //2.Count the number of strings starting with 'A'
        long countStartWithA = stringList.stream().filter(s ->s.startsWith("A")).count();
        System.out.println("Count of strings start with 'A' : "+countStartWithA);
    }

    public void convertCase(){
        //Convert string to uppercase
        List<String> upperCaseList = stringList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(upperCaseList);
    }

    public void removeEmptyString(){
        List<String> nonEmptyStrings = stringList.stream().filter(s->!s.isEmpty()).collect(Collectors.toList());
        System.out.println("Empty Strings removed " + nonEmptyStrings);
    }

    public void joinedString(){
        String joinedString = stringList.stream().collect(Collectors.joining(","));
        System.out.println("Joined String : "+joinedString);
    }

    public void sortStrings(){
        List<String> sortedList = stringList.stream().sorted().collect(Collectors.toList());
        System.out.println("Sorted Strings "+sortedList);
    }

    public void removeDuplicates(){
        List<String> duplicatesRemoved = stringList.stream().distinct().collect(Collectors.toList());
        System.out.println("List of distinct strings : "+duplicatesRemoved);
    }

//    public void frequencyOfEachString(){
//        long frequency = stringList.stream().map()
//    }

    public void longestString(){
        Optional<String> longestString = stringList.stream().max(Comparator.comparingInt(String::length));
        System.out.println("Longest String : "+longestString.get());
    }

    public void concatenateStrings(){
        String concatenatedString = stringList.stream().reduce("",String::concat);
        System.out.println("Concatenated String : "+concatenatedString);
    }

    //the first string that starts with letter "C"
    public void firstString(){
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");
        Optional<String> firstString = names.stream().filter(s->s.startsWith("C")).findFirst();
        System.out.println("first string that starts with letter "+firstString.get());
    }

    public void sumOfSquares(){
        List<Integer> numbers = List.of(1, 2, 3, 4);
        long sum = numbers.stream().map(n->n*n).reduce(0,Integer::sum);
        System.out.println("Sum of squares "+sum);
    }

    //Sort a list of strings in descending (reverse alphabetical) order.
    public void sortDescending() {
        List<String> fruits = List.of("apple", "banana", "cherry", "date");
        List<String> sortDescending = fruits.stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println("Sort a list of strings in descending"+sortDescending);
    }

    //Group words by their length
    public void groupWordsByLength(){
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");
        Map<Integer,List<String>> groupWords = names.stream().collect(Collectors.groupingBy(String::length));
    }

    //Find the maximum number in a list.
    public void findMax(){
        List<Integer> numbers = List.of(10, 20, 5, 80, 30);
        Optional<Integer> max = numbers.stream().max(Integer::compare);
        System.out.println("Find the maximum number in a list."+max);
    }

    //Count how many strings start with "A"
    public void countHowManyStartWithA(){
        List<String> names = List.of("Alice", "Arnold", "Bob", "Charlie",
                "Andrew");
        long count = names.stream().filter(s->s.startsWith("A")).count();
        System.out.println("Count how many strings start with A "+count);
    }

    //String Reversal
    public void stringReversal(String name){
        //Using Streams
        Optional<String> reversed = Arrays.stream(name.split("")).reduce((a,b)->b+a);
        System.out.println("Reversed "+reversed.get());

        //StringBuilder
        String newReversed = new StringBuilder(name).reverse().toString();
        System.out.println("StringBuilder reversed : "+newReversed);
    }

    //Convert a list of lists into a single list
    //flatMap
    public void listOfLists(){
        List<List<String>> nestedList = List.of(
                List.of("a", "b"),
                List.of("c", "d"),
                List.of("e", "f")
        );
        List<String> singleList = nestedList.stream().
                flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("Convert a list of lists into a single list"+singleList);
    }

    //Given a list of integers, return a list of strings "even" or "odd" depending on
    //whether the number is even or odd.

    public void listConversion() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        List<String> listOddOrEven = numbers.stream().map(i->i%2==0 ? "Even" : "Odd").toList();
        System.out.println("Given a list of integers, return a list of strings even or odd "+listOddOrEven);
    }

    public void countOfEachWord(){
        String str = "Java is a programming language";
        long count = Arrays.stream(str.split(" ")).count();
        System.out.println("frequencyOfEachWord : "+count);
    }

    public void countOfEachLetters(){
        String str = "Java is a programming language";
        long count = 0;
        count = Arrays.stream(str.split("")).filter(s->!s.contains(" ")).count();
        System.out.println("countOfLetters : "+count);
    }

    //frequencyOfEachCharacter
    public void frequencyOfEachCharacter(){
        String str = "Java is a programming language";
        Map<Character,Long> frequencyMap = str.chars().
                mapToObj(c->(char)c).filter(c->c!=' ').
                collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        frequencyMap.forEach((k,v)-> System.out.println("Character : "+k+" & Occurence : "+v));
    }

    public void nthHighestElement(int n){
        List<Integer> elements = List.of(1,5,2,7,20,10,12,15);
//        Optional<Integer> nthHighestElement = elements.stream().sorted().skip(n-1).findFirst();
        Optional<Integer> nthHighestElement = elements.stream().sorted().skip(n-1).findFirst();
        System.out.println(n+"th Highest element : "+nthHighestElement.get());
    }

    

}