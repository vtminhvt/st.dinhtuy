definition(
    name: "[Tiện ích]Đồng bộ 2 thiết bị",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
preferences 
{
 section("Chọn")
    {
    	input("sw1","capability.switch",title:"thiết bị 1")
  
      
        input("sw2","capability.switch",title:"thiết bị 2")
       
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
 subscribe(sw1,"switch",sw_1)
 subscribe(sw2,"switch",sw_2)  
}


def sw_1(evt)
{
	
    	if (evt.value=="on")
        { sw2.on()}
        
        if (evt.value=="off")
        {sw2.off() }
	
}
def sw_2(evt)
{
	
    	if (evt.value=="on")
        { sw1.on()}
        
        if (evt.value=="off")
        {sw1.off() }
	
}