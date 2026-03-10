import turtle

integer = java{integer}

t = turtle.Turtle()
t.speed(0)

for i in range(integer % 10 + 3):
    for _ in range(4):
        t.forward(100)
        t.right(90)
    t.right(36)

turtle.done()

result = f"geometric_hover_{integer}"