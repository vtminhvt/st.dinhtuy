definition(
    name: "[006]Khi rời khỏi nhà thiết bị, công tắc TẮT",
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
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
  		input("presence","capability.presenceSensor",title:"Khi thành viên sau rời khỏi nhà:", multiple:true, required:true)
   		input("swCC","capability.switch",title:"Thì danh sách thiết bị, công tắc sau sẽ tắt:", multiple:true, required:true) 	
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
    subscribe(swCC,"switch",sw_CC)
        
    subscribe(presence,"presence",presence_)
    subscribe(iPhone,"presence",iPhone_)
}

def presence_(evt)
{    
	if (evt.value == "present")
	{
            
	}
    else
      if (sel == "on")
    {
    		swCC.off()
			           
    }
}