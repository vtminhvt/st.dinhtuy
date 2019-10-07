definition(
    name: "[An ninh]Mở cửa thì báo động",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Đặt giờ mà hệ thống kiểm tra mức độ an toàn",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences 
{
    section("Chọn thông số kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
        input name: "timeB", type: "time", title: "Thời gian kịch bản có hiệu lực"
        input name: "timeE", type: "time", title: "Thời gian kịch bản kết thúc"
        input("cs1","capability.contactSensor",title:"Khi cảm biến sau ở trạng thái mở")
        input("alamH","capability.alarm",title:"Thì thiết bị sau sẽ báo động")
        input name:"typ",type:"enum", title:"Với kiểu báo động A:còi, L: đèn nhấp nháy", options: ["A","L","AL"], defaultValue:"L"
        input name: "tp", type: "number", title: "Trong khoảng thời gian(giây)?", defaultValue:"15"
    }  
}
def installed()
{
	init() 
}
def updated() 
{ 
	init()
}

def init()
{
    subscribe(cs1,"contact",cs_1)
    subscribe(alamH,"alarm",alam_H)
}
def cs_1(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC=now()
    def p= tp*1000

	def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "open" && sel == "on")
    {
        if (dk1 || dk2)
		{	
        	if(typ=="L") 
            {
        		sendPush("Báo động do phát hiện cửa bị mở")
           		alamH.strobe()
        		schedule(now()+p,alamF) // turn off in 10 second
        	}
            if(typ=="A")
            {
        		sendPush("Báo động do phát hiện cửa bị mở")
        		alamH.siren()
        		schedule(now()+p,alamF) // turn off in 10 second
        	}
        	if(typ=="AL")
        	{
        		sendPush("Báo động do phát hiện cửa bị mở")
        		alamH.both()
           		schedule(now()+p,alamF) // turn off in 10 second
        	}
		}
	}
 }


def alamF()
{
	alamH.off()
}