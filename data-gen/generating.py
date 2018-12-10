import random
import csv

def fName():
    f = open("firstName.csv",'r')
    filesize = 38244
    offset = random.randrange(filesize)
    f.seek(offset)                 
    f.readline()
    first = f.readline().strip()
    return first

def lName():
    l = open("lastName.csv",'r')
    filesize = 695430
    offset = random.randrange(filesize)
    l.seek(offset)
    l.readline()
    last = l.readline().strip()
    return last

def streetAddress():
    s = open("StreetName.csv",'r')
    number = random.randrange(10000)
    filesize = 65227
    offset = random.randrange(filesize)
    s.seek(offset)
    s.readline()
    street = s.readline()
    ret = street.split(',')
    street = ret[0].capitalize()
    
    address = str(number) + " "  + street
    return address

def zipCityState():
    f = open("zipcode.csv",'r')
    filesize = 4318308
    offset = random.randrange(filesize)
    f.seek(offset)
    f.readline()
    line = f.readline()
    ret = line.split(',')
    zip = ret[0].strip('"')
    city = ret[2].strip('"').lower()
    state = ret[3].strip('"')
    
    return [city, zip, state]

def ssn():
    x = str(random.randrange(1000))
    y = str(random.randrange(100))
    z = str(random.randrange(10000))

    if (len(x) < 3):
        x = (3-len(x)) * "0" + x
    if (len(y) < 2):
        y = (2-len(y)) * "0" + y
    if (len(z) < 4):
        z = (4-len(z)) * "0" + z

    s = x + "" + y + "" + z
    return s

def phone():
    x = str(random.randrange(100,1000))
    y = str(random.randrange(100,1000))
    z = str(random.randrange(1000,10000))

    s = x + "" + y + "" + z
    return s

def gender():
    if (random.randrange(2) == 1):
        return "m"
    else:
        return "f"

def annualIncome():
    return random.randrange(10000,500000,100)

def customer():
    c = [name(), address(), ssn(), phone(), gender()]
    return c

def dealer():
    d = [name() + " Dealership", address()]
    return d

def brand_entry(i):
    brands = ["ford", "toyota", "bmw", "dodge", "tesla", "jeep"]
    countries = ["usa", "canada", "germany", "china"]
    return [brands[i], countries[random.randrange(len(countries))]]

def model_entry(i):
    ret = [i]
    models = ["convertible", "camry", "fusion", "hatchback", "focus", "prius", "fiesta", "ranger", "sonata", "jetta", "voyager", "wrangler", "thisone", "thatone", "otherone"]
    ret.append(models[random.randrange(15)])
    ret.append(random.randrange(1990,2018))
    ret.append(random.randrange(10,30))
    gas = ["diesel", "unleaded", "electric"]
    ret.append(gas[random.randrange(3)])
    ret.append(brand_entry(random.randrange(6))[0])
    return ret
    
def vin():
    alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    s = ""
    for i in range(17):
        s = s + alphanum[random.randrange(len(alphanum))]
    return s

def color():
    colors = ["red","blue","yellow","white","black","silver","chrome"]
    return colors[random.randrange(len(colors))]

def transmission():
    transmissions = ["automatic", "manual"]
    return transmissions[random.randrange(len(transmissions))]

def engine():
    engines = ["type1","type2", "type3", "type4", "type5"]
    return engines[random.randrange(len(engines))]

def price():
    return random.randrange(10000,100000,1000)

def date():
    year = random.randrange(2010,2018)
    month = random.randrange(1,12)
    day = 0
    if (month in [1,3,5,7,8,10,12]):
        day = random.randrange(1,31)
    elif (month in [4,6,9,11]):
        day = random.randrange(1,30)
    else:
        day = random.randrange(1,28)
    return str(year)+"-"+str(month)+"-"+str(day)

def main():

    veh = open("vehicle.csv", "w")    
    inv = open("inventory.csv", "w")
    dea = open("dealer.csv", "w")
    sal = open("sale.csv", "w")
    bra = open("brand.csv", "w")
    mod = open("model.csv", "w")

    cus = open("customer.csv", "w")


    for i in range(10):
        inventory_entry = [i]
        inventory_entry.append(streetAddress())
        inventory_entry.extend(zipCityState())
        inv.write(str(inventory_entry).strip('[]')+"\n")
    
    for i in range(6):
        bra.write(str(brand_entry(i)).strip('[]')+"\n")
 
    for i in range(50):
        dealer_entry = [i]
        dealer_entry.append(fName())
        dealer_entry.append(lName())
        dealer_entry.append(phone())
        dealer_entry.append(brand_entry(random.randrange(6))[0])
        dealer_entry.append(random.randrange(10))
        dea.write(str(dealer_entry).strip('[]')+"\n")

    for i in range(50):
        mod.write(str(model_entry(i)).strip('[]')+"\n")

    for i in range(50):
        customer_entry = [ssn()]
        customer_entry.append(fName())
        customer_entry.append(lName())
        customer_entry.append(phone())
        customer_entry.append(gender())
        customer_entry.append(annualIncome())
        customer_entry.append(streetAddress())
        customer_entry.extend(zipCityState())
        cus.write(str(customer_entry).strip('[]')+"\n")

    cus = open("customer.csv","r")
    csv_reader = csv.reader(cus, delimiter=',')
    ssns = []
    for line in csv_reader:
        ssns.append(line[0].strip('" ').strip("'"))
    random.shuffle(ssns)
        
    for i in range(100):
        vehicle_entry = [vin()]
        vehicle_entry.append(color())
        vehicle_entry.append(transmission())
        vehicle_entry.append(engine())
        vehicle_entry.append(random.randrange(50))
        vehicle_entry.append(random.randrange(10))
        vehicle_entry.append(ssns[random.randrange(50)])
        veh.write(str(vehicle_entry).strip('[]')+"\n")


    veh = open("vehicle.csv","r")
    csv_reader = csv.reader(veh, delimiter=',')
    cuss = []
    vehs = []
    for line in csv_reader:
        cuss.append(line[6].strip("' "))
        vehs.append(line[0].strip('" '))
    dea = open("dealer.csv","r")
    csv_reader = csv.reader(dea, delimiter=',')
    deas = []
    for line in csv_reader:
        deas.append(line[0].strip("'"))
    
    for i in range(25):
        sale_entry = [i]
        sale_entry.append(price())
        sale_entry.append(date())
        sale_entry.append(vehs[i].strip("'"))
        sale_entry.append(cuss[i])
        sale_entry.append(deas[i])
        sal.write(str(sale_entry).strip('[]')+"\n")
        
    
main()
