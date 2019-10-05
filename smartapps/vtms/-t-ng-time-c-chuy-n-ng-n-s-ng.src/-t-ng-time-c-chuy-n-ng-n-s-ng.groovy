definition(
    name: "[Tự động, Time]Có chuyển động đèn sáng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Tự động, điều khiển đèn khi có chuyển động PIR",
    category: "Safety & Security",
iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test: OK
preferences 
{
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản:", options: ["on","off"], defaultValue:"off"
        input name: "timeB", type: "time", title: "Chọn thời gian bắt đầu"
        input name: "timeE", type: "time", title: "và thời gian kết thúc:"
        input("motionCD", "capability.motionSensor",title:"Khi phát hiện chuyển động trên cảm biến:", multiple:true, required:true)
        input("sw1","capability.switch",title:"thì công tắc sau sẽ sáng:", multiple:true, required:true)
        input name: "timeofP", type: "number", title: "trong số phút:", defaultValue:"3"
        
        
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
  	subscribe(sw1,"switch",sw_1) 
}

def motion_CD(evt)
{
	def timeofB = timeToday(timeB)
	def timeofE= timeToday(timeE)
	def timeC=now()
	
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "active" && sel == "on")
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
	def t=motionCD.currentValue("motion")
    if(t=="active") 
    {
    	def timeP=timeofP*60	
    	runIn(timeP,lightOFF)    	
    }
    else
    {
    	sw1.off()
    }   
}