integer = java{integer}
text = java{text}

def count_vowels(s):
    vowels = "aeiouAEIOU"
    count = 0
    for c in s:
        if c in vowels:
            count += 1
    return count

vowel_count = count_vowels(text)

factorial = 1
for i in range(1, integer + 5):
    factorial *= i

value = factorial + vowel_count

result = "factorial_part={0},vowels={1},value={2}".format(factorial, vowel_count, value)