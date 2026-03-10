import turtle

integer = java{integer}

t = turtle.Turtle()
t.speed(0)
colors = ["red","orange","yellow","green","blue","purple"]

for i in range(360):
    t.pencolor(colors[i % len(colors)])
    t.forward(i * 0.5)
    t.right(59)

turtle.done()

result = f"color_spiral_done_{integer}"