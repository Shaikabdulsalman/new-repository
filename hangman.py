import random

# Dictionary of words related to full-stack development and their clues
words = {
    "frontend": "The part of a web application that the user interacts with.",
    "backend": "The server-side of a web application responsible for logic and database operations.",
    "database": "A structured collection of data organized for efficient retrieval.",
    "API": "A set of rules and protocols for building and interacting with software applications.",
    "framework": "A pre-built, reusable set of components that simplify the development of software.",
    "HTML": "A markup language used for creating the structure of web pages.",
    "CSS": "A styling language used to control the appearance of web pages.",
    "JavaScript": "A programming language used to add interactivity to web pages.",
    "SQL": "Structured Query Language used for managing and manipulating relational databases.",
    "server": "A computer or software that provides services to other computers or clients.",
    "deployment": "The process of making a web application live and accessible to users.",
    "version control": "A system for managing changes to code, documents, or files over time.",
}

# Choose a random word from the dictionary
word = random.choice(list(words.keys()))
clue = words[word]

# Initialize the guessed word with underscores
guessed_word = ['_'] * len(word)

# Track the number of incorrect guesses
incorrect_guesses = 0
max_attempts = 11  # Increased to allow an extra chance

print("Welcome to the Full Stack Guessing Game!")
print("Guess the letters to uncover the word related to full-stack development.")
print("Here's your clue:", clue)
print(" ".join(guessed_word))

while incorrect_guesses < max_attempts:
    guess = input("\nEnter a letter or the full word: ").lower()

    if len(guess) == 1:
        if not guess.isalpha():
            print("Please enter a valid letter.")
            continue

        if guess in guessed_word:
            print("You've already guessed this letter.")
            continue

        if guess in word:
            for index, letter in enumerate(word):
                if letter == guess:
                    guessed_word[index] = guess
            print(" ".join(guessed_word))
            
            if "_" not in guessed_word:
                print("Congratulations! You've uncovered the word:", word)
                break
        else:
            incorrect_guesses += 1
            remaining_attempts = max_attempts - incorrect_guesses
            if remaining_attempts == 1:
                print("Incorrect guess. You have 1 chance left.")
            else:
                print(f"Incorrect guess. You have {remaining_attempts} chances left.")
    elif guess == word:
        print("Congratulations! You've guessed the correct word:", word)
        break
    else:
        print("Incorrect guess. Please try again.")

if "_" in guessed_word:
    print("Sorry, you've run out of chances. The word was:", word)