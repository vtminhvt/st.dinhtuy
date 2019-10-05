definition(
    name: "[Tự động,time]Khi về nhà thiết bị, công tắc BẬT",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
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
        input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu"
        input name: "timeE", type: "time", title: " và gian kết thúc:"
        input("presence1","capability.presenceSensor",title:"Khi phát hiện thành viên trở về")
        input("sw1","capability.switch",title:"Thì bật thiết bị, công tắc sau đây:",multiple:true, required:true)
        input name: "timeofP", type: "number", title: "với số phút:", defaultValue:"1"
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
	
  	subscribe(sw1,"switch",sw_1) 
	subscribe(presence1,"presence",presence_1)
   
}

def presence_1(evt)
{
	def timeofB = timeToday(timeB)
	def timeofE= timeToday(timeE)
	def timeC=now()
    
	 def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "present" && sel == "on")
    {
    
        if (dk1 || dk2)
		{
            sw1.on()
            def timeP=timeofP*60
            runIn(timeP,lightOFF)
		}
	}	
	else
	{
		//log.debug "timeP= ${timeP}"
	}
}

def lightOFF()
{
	sw1.off()
    //log.debug "Đèn đã tắt lightOFF()"
}