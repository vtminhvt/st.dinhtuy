definition(
        name: "[004]Hẹn giờ bật/tắt thiết bị, công tắc",
        namespace: "VTMS",
        author: "Võ Thanh Minh",
        description: "Hẹn giờ bật tắt nhiều thiết bị cùng lúc",
        category: "Safety & Security",
        iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")


preferences 
{
    section("Tùy chọn thông số kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaulValue:"off"
    
    
        input name: "timeB", type: "time", title: "Chọn giờ BẬT"
        input name: "timeE", type: "time", title: "Chọn giờ TẮT"
    
        input("sw1","capability.switch",title:"Chọn thiết bị, công tắc nhận lệnh điều khiển", multiple:true, required:true)

    }
}
def installed() 
{
	init()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
}

def updated() 
{
	init()
    unschedule()
	schedule(timeB, lightON)
	schedule(timeE, lightOFF)
   	
}
def init()
{
  	subscribe(sw1,"switch",sw_1) 
}
def lightON()
{
//	def sw1 = switches.find { it.displayName == theName }
if (sel=="on")	sw1.on()
}

def lightOFF()
{
//	def sw1 = switches.find { it.displayName == theName }
if (sel=="on") sw1.off()
}