n = 15

def boolean(x):
    return "TRUE" if x else "FALSE"

print("-- Boxes")
for i in range(n):
    sql = "INSERT INTO BOXES (ID, INDOOR, WINDOWS, DAILY_RATE, AREA, NAME) VALUES ({}, {}, {}, {}, {}, '{}');".format(i, boolean(i%2), boolean(i%2), i, i, "box_{}".format(i))
    print(sql)

print("-- Bookings")
for i in range(n):
    sql = "INSERT INTO BOOKINGS (ID, BEGIN_TIME, END_TIME, CUSTOMER_NAME) VALUES ({}, '{}', '{}', '{}');".format(i, '2017-03-10', '2017-03-30', 'customer_{}'.format(i))
    print(sql)
