definition(
    name: "[009-T]Mở cửa đèn sáng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Tự động, an ninh, có thời gian",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test:OK

preferences 
{
    section("Kích hoạt hoạt động")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
        input name: "timeB", type: "time", title: "Giờ bắt đầu",defaultValue:"6:30PM"
        input name: "timeE", type: "time", title: "Giờ kết thúc", defaultValue:"4:30AM"
        input("cs1","capability.contactSensor",title:"Chọn cảm biến")
        input("sw1","capability.switch",title:"Chọn công tắc cần bật", multiple:true, required:true)
       	input name: "timeofP", type: "number", title: "Đèn sáng bao lâu(phút)?", defaultValue:"1"     
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
	subscribe(cs1,"contact",cs_1) 
  	subscribe(sw1,"switch",sw_1) 
}
def cs_1(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    def timeP=timeofP*60
    
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "open" && sel == "on")
    {
    
        if (dk1 || dk2)
        {
            sw1.on()
        }
    }
    if(evt.value=="closed")
    {
            sw1.off()
    }
}