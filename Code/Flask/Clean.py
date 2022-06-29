import pandas as pd
import re
def isNaN(string):
    return string != string
def read(name):
    df=pd.read_csv(name,index_col=0)
    df = df[df['Names'].notna()]
    df=df.reset_index()
    del df["index"]
    df = df.drop_duplicates()
    df=df.reset_index()
    del df["index"]
    df = df.drop_duplicates(subset="Names",keep="first")
    df=df.reset_index()
    del df["index"]
    return df
def clean_stars(df):
    for i in range(len(df)):
        if not isNaN(df['Stars'][i]):
            df['Stars'][i]=df['Stars'][i].replace('\xa0',"")

            
def clean_prices(df):
    prices=df["Price"].to_list()
    print(len(prices))
    cl_prices=[]
    for i in prices:
        if not isNaN(i):
            pric=i.replace(",",'')
            pric=pric.split("MAD\xa0")
            cl_prices.append([ float(j)  for j in pric[1:]])
        else:
            cl_prices.append("")
    print(len(cl_prices))
    return(cl_prices)
def clean_time(df):
    cl_checkin=[]
    cl_checkout=[]
    checkin=df["check in"].to_list()
    checkout=df["Check-out"].to_list()
    for i in range(len(checkin)):
        if not isNaN(checkin[i]) and not isNaN(checkout[i]):
            cl_checkin.append(checkin[i].strip("\n").replace("&nbsp;",""))
            cl_checkout.append(checkout[i].strip("\n").replace("&nbsp;",""))
        else:
            cl_checkin.append("")
            cl_checkout.append("")
    df["check in"]=cl_checkin
    df["Check-out"]=cl_checkout
    return cl_checkin,cl_checkout
def clean_aer(df):
    clean_aer=[]
    are=df["Aeroports"].to_list()
    for i in are:
        i=i.replace("\n",' ').strip().replace("miles","").strip('Closest Airports       ')
        aa=i.split("  ")
        clean_aer.append(aa)
    df["Aeroports"]=[j[0] for  j in clean_aer]
    df["distance aeroport"]=[j[1] for j in clean_aer]
    return clean_aer
def clean_list(lis):
    nouns=[]
    dist=[]
    for i in lis:
        if not isNaN(i):
            test = re.sub('\[',"]",str(i))
            # test.strip("]")
            # test.replace(",","")
            test=test.replace('"',"'")
            s=test.split(']')
            ss=[j.strip("'") for j in s if j and j!=', ']
            sss=[j.strip("'").split("', '") for j in ss]
            nouns.append([sss[j][0] for j in range(len(sss)) ])

            ll=[]

            for c in sss:   
                if " miles away" in c[1] :
                    l=re.split(" miles away",c[1])
                    l[0]+= " mi"
                elif " min" in c[1]:
                    l=re.split(" min",c[1])
                    l[0]+=" min"
                elif " mi" in c[1] :
                    l=re.split(" mi",c[1])
                    l[0]+= " mi"
                ll.append(l)
            dist.append(ll)
        else:
            dist.append([""])
            nouns.append(["",""])
    return nouns,dist
def clean_nearby(df):
    list1=df['near_res'].to_list()
    list2=df['near_att'].to_list()
    ll1=clean_list(list1)
    ll2=clean_list(list2)
    res1=[]
    for i in ll1[0]:
        c=""
        for j in i :
             c+=j+","
        res1.append(c)
    res2=[]
    for i in ll2[0]:
        c=""
        for j in i :
            c+=j+","
        res2.append(c)
    df['near_res']=res1
    df['near_att']=res2
    if "near_att" in  df.columns.tolist():
        list3=df['near_att'].to_list()
        ll3=clean_list(list3)
        res3=[]
        for i in ll3[0]:
            c=""
            for j in i :
                c+=j+","
            res3.append(c)
        df['near_hot']=res3   
def clean_list_att(lis):
    nouns=[]
    dist=[]
    typ=[]
    for i in lis:
        if not isNaN(i):
            test = re.sub('\[',"]",str(i))
            # test.strip("]")
            # test.replace(",","")
            test=test.replace('"',"'")
            s=test.split(']')
            ss=[j.strip("'") for j in s if j and j!=', ']
            sss=[j.strip("'").split("', '") for j in ss]
            nouns.append([sss[j][0] for j in range(len(sss)) ])
            print(sss)

            ll=[]

            for c in sss: 
                if len(c)==3:
                    dist.append(c[1])
                    typ.append(c[2])
                else:
                    dist.append("")
                    typ.append("")
        else:
            dist.append([""])
            nouns.append(["",""])
    return nouns,dist,typ
def clean_nearby_att(df):
    list1=df['near_res'].to_list()
    list2=df['near_att'].to_list()
#     list3=df['near_hot'].to_list()
    ll1=clean_list_att(list1)
    ll2=clean_list_att(list2)
#     ll3=clean_list_att(list3)
    res1=[]
    for i in ll1[0]:
        c=""
        for j in i :
             c+=j+","
        res1.append(c)
    res2=[]
    for i in ll2[0]:
        c=""
        for j in i :
            c+=j+","
        res2.append(c)
#     res3=[]
#     for i in ll3[0]:
#         c=""
#         for j in i :
#             c+=j+","
#         res3.append(c)
    df['near_res']=res1
    df['near_att']=res2
#     df['near_hot']=res3
def merge(df1,df2):
    new_df=pd.DataFrame()
    booknames=df1["Names"].to_list()
    tripnames=df2["Names"].to_list()
    for i in range(len(booknames)):
        print(i)
        eddd=[]
        booknames[i]=booknames[i][:-32]
        for j in tripnames:
           
            ed = nltk.edit_distance(booknames[i].lower(),j.lower())
            eddd.append(ed)
        mi=min(eddd)
        
        index_mi=eddd.index(mi)
        print(booknames[i],tripnames[index_mi],mi)
        temp= {'Names':booknames[i], 'Names1':tripnames[index_mi],'Stars':df2["stars"][index_mi],"address":df2["address"][index_mi],"Tel":df2["tel"][index_mi],"Restaurants":df2["nearby restaurants"][index_mi],"Site touristique":df2["nearby attractions"][index_mi],"Description":df2["description"][index_mi],"Styles":df2["styles"][index_mi],"languages":df2["languages"][index_mi],"Properties":df2["proprietes"][index_mi],"Type":df1["Type"][i],"check in":df1["Check-in"][i],"Check-out":df1["Check-out"][i],"Aeroports":df1["Aeroports"][i]}
        new_df=new_df.append(temp, ignore_index = True)
    return  new_df