definition(
    name: "[012]Bật báo động khi có chuyển động 2 bước",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản cho an ninh",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test:OK 

preferences 
{
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"on"
        input name: "timeB", type: "time", title: "Giờ bắt đầu",defaultValue:"23:59"
        input name: "timeE", type: "time", title: "Giờ kết thúc",defaultValue:"04:00"
        input("motionCD", "capability.motionSensor",title:"Tại cảm biến")
        input name: "tp1", type: "number", title: "Báo động lần 1 trong bao nhiêu giây?", defaultValue:"20"
        input name:"typ1",type:"enum", title:"Với kiểu báo động lần 1: A: Còi hú; L: Đèn nhấp nháy", options: ["A","L"], defaultValue:"L"
        input name: "tp", type: "number", title: "Sau bao nhiêu(giây) thì kiểm tra lần 2 kể từ khi kết thúc lần 1", defaultValue:"180"
        input name: "tp2", type: "number", title: "Báo động lần 2 trong bao nhiêu giây?", defaultValue:"20"
        input name:"typ2",type:"enum", title:"Với kiểu báo động lần 2: A: Còi hú; L: Đèn nhấp nháy", options: ["A","L"], defaultValue:"L"
     input("alamH","capability.alarm",title:"Thiết bị nào phát âm thanh")
    }
    
 }
 
def installed() 
{
    init()
}

def updated() 
{
	unschedule()
   	init()	
}

def init()
{
	subscribe(motionCD,"motion",motion_CD)
  	subscribe(alamH,"alarm",alam_H) 
}
def initialize() {
    // execute handlerMethod every hour on the half hour.
    //schedule("0 30 * * * ?", alamF)
}
def motion_CD(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC= now()
    def p1= tp1*1000
    def p= tp*1000 
    def pp1=p+p1
    
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "active" && sel == "on")
    {
    
        if (dk1 || dk2)
    		{ 
            	def t_m=motionCD
            	sendPush("Báo động bước 1, Kiểu ${typ1} ${evt.displayName} là ${evt.value} ")
               if(typ1=="L") { alamH.strobe()}
               if(typ1=="A") {alamH.siren()}
               if(typ1=="AL") { alamH.both()}  
              // schedule(now()+p1,alamF) // turn off in 10 second
               schedule(now()+pp1,laplai) // loop again in p second
    		}
    	}
}

def laplai()
{
	alamH.off()
    
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC= now()
    def p2= tp2*1000  
    def t_m=motionCD.currentValue("motion")
    
    if (t_m == "active")
    {  
    	sendPush("Báo động bước 2, Kiểu ${typ2}")
        if(typ2=="L") {alamH.strobe()}
        if(typ2=="A") {alamH.siren()}
        if(typ2=="AL") {alamH.both()}  
        schedule(now() + p2,alamF)
    }
     
}

def alamF()
{
	alamH.off()
    
}

def alam_H(evt)
{
  if (evt.value == "strobe") 
  	{
    	sendPush("Báo động đang nhấp nháy đèn")
  	} 
  	if (evt.value == "siren") 
  	{
    	sendPush( "Báo động đang phát âm thanh")
  	}
  	if (evt.value == "both") 
  	{
    	sendPush ("Báo động đang phát đèn và âm thanh")
  	}
  	if (evt.value == "off") 
  	{
    	sendPush("Đã tắt báo động")
  	}
}