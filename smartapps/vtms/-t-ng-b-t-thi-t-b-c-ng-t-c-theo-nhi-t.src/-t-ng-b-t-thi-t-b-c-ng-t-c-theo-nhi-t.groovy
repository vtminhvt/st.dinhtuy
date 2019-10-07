definition(
    name: "[Tự động] Bật thiết bị, công tắc theo nhiệt độ",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Khi đạt đến nhiệt độ bất kỳ thì thiết bị sẽ bật và thấp hơn nhiệt độ đã cho thì sẽ tắt",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences {
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
    	input("sw1","capability.switch",title:"Chọn thiết bị bạn muốn điều khiển")   
    	input ("temp1", "capability.motionSensor",title:"Chọn cảm biến cung cấp nhiệt độ")
        input name:"nd1",type:"number", title:"Nhập nhiệt độ C",defaultValue:"40"
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
	unsubscribe()
    subscribe(sw1,"switch",sw_1)
    subscribe(temp1,"motionSensor",temp_1)
    
}

def temp_1(evt)
{
	def ctemp = temp1.currentState("temperature")
    log.debug "temperature value as a string: ${currentState.value}"
    
	if (ctemp.value>=nd1)
		{
        sw1.on()
        sendPush("${evt.displayName} đang mở tại nhiệt độ ${ctemp.value} ")
		}
    else
    	{
		sw1.off()	
         sendPush("${evt.displayName} đang tắt tại nhiệt độ ${ctemp.value} ")
        }

}

