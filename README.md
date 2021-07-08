# Calculator
## Интефейс
* Для набора выражения нажимайте на кнопки цифр и математических операций. Не поместившееся на экране выражение можно пролистывать.
     
<img
     src="https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/res/drawable/portrait.jpg"
     width=170
     height=320/>
     
* Для удаления последнего символа нажмите кнопку **backspace**
* Для того чтобы полностью отчистить полосы выражения и результата нажмите **C**
* Для большего количесва операций поверните экран

<img
     src="https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/res/drawable/landscape.jpg"
     width=450
     height=200/>
 * Для подсчёта результата нажмите кнопку **=**. Результат будет выведен во второй полосе.

## Парсер выражений
Для [парсера выражений](https://github.com/FedosOnGIT/Calculator/blob/master/app/src/main/java/com/example/calculator/Calculator.kt) используется метод рекурсивного спуска с приоритетатми, где
* 0 - операции **+** и **-**
* 1 - операции **\*** и **\\**
