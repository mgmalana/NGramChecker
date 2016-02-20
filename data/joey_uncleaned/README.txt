The text files in this folder are the words, lemmas, and tags in each excel sheet.
The lines in each file group (ex. a56_200_words , a56_200_lemmas, a56_200_tags) are mapped 1:1:1 with respect to their line number
in the spreadsheet.

The errors in the 'errors' folder is produced by a software that checks if the number of tokens in each line in each file group are equal.
If the number of tokens are not equal, then the system will prompt you to fix the error by aligning the tokens for you to identify what the errors are.
Of course, there will be misalignments because the number of tokens are not equal.
It also tells you the number of tokens (lengths) of lemmas, pos, and words and which line number the error is.

To fix the errors, you will need to open the files in the file group that has errors and locate the line number where the misalignment is.
Common errors I have seen are: 
forgetting spaces between words and punctuation marks(commas, period, quotation marks), 
lack of underscores to connect the words that should have a single tag (ex. ang_mga, sa_likod),
missing or extra POS tag.

As an example, consider the error file a56_211_errors.txt. It has a single error line at line 33 where there are 15 lemmas, 16 pos tags, and 15 word tags. 
So, open the files a56_211_words, lemmas, and tags.txt and then observe what the error might be based on the alignments in the errors file. 
As you can observe, the period in the last token lacked a space before it which resulted to a misalignment of the tokens. 
Simply put a space before the period in the words and lemmas file and the error is fixed.

There would also be lines with the lemma and words "NO TRANSLATION" and a blank POS tag. Kindly delete those lines.
Please be really careful when deleting lines. It might be really hard to fix if the number of lines are unequal.

Note: The software implemented to align the tokens may not work perfectly especially if there are tokens that are of character length >= 20.


If you have any questions or requests, please do not hesitate to email me. 

Good luck and thank you for your work!

- Matthew
