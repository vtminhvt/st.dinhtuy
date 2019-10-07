definition(
    name: "[Tự động]Bật đèn từ cảm biến camera thông qua IFTTT",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Đồng bộ chuyển động với một công tắc ảo thông qua IFTTT",
    category: "Safety & Security",
iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test: OK
preferences 
{
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"     
        input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu có hiệu lực"
        input name: "timeE", type: "time", title: "Đặt thời gian kết thúc hiệu lực"
        input("motionCD", "capability.switch",title:"Khi phát hiện chuyển động, tương ứng với công tắc ảo:", multiple:true, required:true)
        input("sw1","capability.switch",title:"Thì thiết bị, công tắc sau bật:", multiple:true, required:true)
        input name: "timeofP", type: "number", title: "Trong thời gian(phút):", defaultValue:"3"
        
        
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
	subscribe(motionCD,"switch",motion_CD)
  	subscribe(sw1,"switch",sw_1) 
}

def motion_CD(evt)
{
	def timeofB = timeToday(timeB)
	def timeofE= timeToday(timeE)
	def timeC=now()
	
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "on" && sel == "on")
    {
    
        if (dk1 || dk2)
		{
            sw1.on()
            def timeP=timeofP*60
            runIn(timeP,lightOFF)
            //
		}
	}	
	else
	{
		//log.debug "timeP= ${timeP}"
	}
}

def lightOFF()
{
	def t=motionCD.currentValue("switch")
    if(t=="on") 
    {
    	def timeP=timeofP*60	
    	runIn(timeP,lightOFF)    	
    }
    else
    {
    	motionCD.off()
        sw1.off()
    }   
}