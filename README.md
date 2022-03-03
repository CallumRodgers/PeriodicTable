# Tabela Periódica
Uma implementação em Java de uma Tabela Periódica interativa. A base do código foi retirada de um
projeto pessoal ainda não lançado, o **SpaceISE**.

## Funcionalidade
1. A tabela possui **18 colunas (grupos)** e **7 linhas (períodos)**. Cada um desses eixos da matriz possui
   um número respectivo, que é interativo e ao clicá-lo, a tabela irá desativar todos os outros elementos
   que não sejam parte desta linha ou coluna. Os casos dos Lantanídeos e dos Actinídeos são especiais, pois
   tecnicamente eles fazem parte dos períodos 6 e 7, respectivamente. Portanto, ao realçar tais linhas, eles
   também serão ativados.

2. A tabela também pode agrupar os elementos baseada em outras características: a massa atômica,
   a densidade, e a eletronegatividade. Cada agrupamento terá um esquema de cores diferente.

3. Clicar em um elemento abrirá uma página que cobre a janela inteira, mostrando todos os valores e
   atributos disponíveis para aquele elemento, além de uma visualização animada do átomo.

4. Você pode mudar a unidade de temperatura padrão ao selecionar pelo menu de cima. Porém, sua escolha
   não será guardada internamente para a próxima vez que o programa rodar. Ele sempre abrirá com o padrão
   de graus **Celsius (°C)**.

## Licença
A tabela está licensiada sob a **MIT License**, que permite quase todos os usos, não fornecendo
garantia ou responsabilidade, tudo contanto que os direitos autorais e a licença sejam preservados.
Veja o arquivo "LICENSE.txt" para detalhes e o texto em si.



# Periodic Table (English)
A Java implementation of an interactive Periodic Table, in Portuguese. Its core code was extracted from a
much larger personal project yet to be released, **SpaceISE**.

## Functionality
1. The table has **18 columns** (groups) and **7 rows** (periods). Each line in this matrix has a respective number label,
   which is interactable and clicking on it will enable only the elements that are in the line.
   The Lanthanides and Actinides are two special rows, because they are actually part of periods 6 and 7,
   respectively. Thus, if you highlight such periods, those groups will be enabled as well.
   The table can also group elements based on other characteristics: their atomic mass, density,
   and electronegativity. Each mode will have its own colour scheme.

2. Clicking on an element will open up a page that covers the entire window, displaying all the available
   attributes for it, as well as an atom visualisation.

3. You can change the default temperature unit by selecting it on the drop-down menu. Your choice won't be stored for the
   next time the program runs, however. It will always begin with **Celsius (°C)** as the default.

## License
The table is licensed under the **MIT License**, which allows basically everything, except for
warranty and liability, and all under the conditions that the copyright notice is preserved.
See the "LICENSE.txt" file for details and the actual license text.