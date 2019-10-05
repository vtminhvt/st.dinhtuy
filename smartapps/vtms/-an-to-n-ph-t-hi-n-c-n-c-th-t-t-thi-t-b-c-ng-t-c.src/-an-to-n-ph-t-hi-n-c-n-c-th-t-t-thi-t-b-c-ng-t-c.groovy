definition(
    name: "[An toàn]Phát hiện có nước thì tắt thiết bị, công tắc",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển, Nhiều thiết bị, An toàn, Máy bơm",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
 
section ("Tùy chọn thông số kịch bản")
{	
        input("wtRain","capability.waterSensor",title:"Có nước tại cảm biến", multiple:true, required:true)   
    	input("swGR","capability.switch",title:"Thì thiết bị, công tắc sẽ tắt", multiple:true, required:true)
        input name:"txt",type:"text", title:"Với nội dung thông báo là:",defaultValue:" "
         
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
   
   subscribe(swGR,"switch",sw_GR)
   subscribe(wtRain,"water",wt_Rain)
}

def wt_Rain(evt)
{
	if (evt.value=="wet") 
    {
    	swGR.off()
    }
}