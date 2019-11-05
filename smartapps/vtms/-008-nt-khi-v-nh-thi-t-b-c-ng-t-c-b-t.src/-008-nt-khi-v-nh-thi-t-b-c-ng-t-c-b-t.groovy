definition(
    name: "[008-nT]Khi về nhà thiết bị, công tắc BẬT",
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
        input("presence_1","capability.presenceSensor",title:"Khi phát hiện thành viên trở về")
        input("sw1","capability.switch",title:"Thì bật thiết bị, công tắc sau đây:",multiple:true, required:true)
        input name: "timeofP", type: "number", title: "với số phút:", defaultValue:"1"
    }}
    
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
    subscribe(swCC,"switch",sw_CC)
        
    subscribe(presence1,"presence",presence_1)
    
}

def presence_1(evt)
{    
	if (evt.value == "present")
	{
            
	}
    else
      if (sel == "on")
    {
    		swCC.on()
			           
    }
}