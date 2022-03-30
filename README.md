# JavaSudokuAndroid

A Test Project. Per a friend's request, I ported [Java Sudoku Player](!https://github.com/Andrew-McCall/JavaSudoku) to android 12.
The Logic Class is a direct copy but the board is rewritten as a View and Canvas for the andorid OS (There is no menu). This was more of a proof of concept than a proper project to be proud of.

## Known Bugs
- ~~Timer permantly counts out~~
- Game (rarely) resets randomly, I think Android doesnt play nicely with the whole app being loaded on the OnCreate of the Board.
- Menu wasn't ported over.
- Some Screen ratios crop the game. (Made for Pixel 3a XL, 1440x2560)
