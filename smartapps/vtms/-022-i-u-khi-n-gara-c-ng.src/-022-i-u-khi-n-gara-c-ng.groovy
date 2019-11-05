definition(
    name: "[022] Điều khiển gara, cổng",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển Gara, cửa cuốn",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
    section("Chọn thông số cho kịch bản")
    {
    	input("swGR","capability.switch",title:"Gán công tắc tương ứng nút ấn điều khiển", multiple:true, required:true)             
       	input name:"txt",type:"text", title:"Với thông báo gì khi được điều khiển",defaultValue:" "
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
}

def sw_GR(evt)
{
if (evt.value == "on")
	{
	   swGR.off()
       sendPush("${txt}")
	}
}