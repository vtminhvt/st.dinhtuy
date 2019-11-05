definition(
    name: "[005] Đặt thời gian hoạt động TỐI ĐA thiết bị, công tắc",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")

preferences 
{
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"on"
        input("sw1","capability.switch",title:"Chọn danh sách thiết bị, công tắc cần đặt ngưỡng thời gian", multiple:true, required:true)
        input name: "tmax", type: "number", title: "nhập số phút tối đa:", defaultValue:"7"
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
}

def sw_1(evt)
{
	def te=tmax *60
    if (sel=="on" && evt.value=="on") 
	{
    	runIn(te,dOFF)   
    }
}

def dOFF()
{
	sw1.off()
}