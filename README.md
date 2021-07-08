# Calculator
## Интефейс
* Для набора выражения нажимайте на кнопки цифр и математических операций. Не поместившееся на экране выражение можно пролистывать.

<p align="center">
     <img
          src="https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/res/drawable/portrait.jpg"
          width=170
          height=320/>
 </p>
     
* Для удаления последнего символа нажмите кнопку **backspace**
* Для того чтобы полностью отчистить полосы выражения и результата нажмите **C**
* Для большего количесва операций поверните экран

<p align="center">
<img
     src="https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/res/drawable/landscape.jpg"
     width=450
     height=200/>
</p>
     
 * Для подсчёта результата нажмите кнопку **=**. Результат будет выведен во второй полосе.
 
 <p align="center">
      <img
           src="https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/res/drawable/result.jpg"
           width=170
           height=320/>
</p>

## Парсер выражений
Для [парсера выражений](https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/java/com/example/calculator/Calculator.kt) используется метод рекурсивного спуска с приоритетатми, где
* 0 - операции **+** и **-**
* 1 - операции **\*** и **\\**
* 2 - операция унарного **-**
* 3 - операция возведения в степень **^**
* 4 - Все оставшиеся унарные операции, а также чтение числа и распознавание выражений в **()**
