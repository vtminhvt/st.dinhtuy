definition(
    name: "[Tiết kiệm]Đặt thời gian hoạt động tối đa công tắc, thiết bị",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển theo thời gian tối đa",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")

preferences 
{
    section("Chọn thông số cho kịch bản")
    {
        input name:"sel",type:"enum", title:"Chọn ON để kích hoạt kịch bản", options: ["on","off"], defaultValue:"off"
        input("sw1","capability.switch",title:"Chọn danh sách thiết bị, công tắc cần giới hạn thời gian tối đa", multiple:true, required:true)
        input name: "tmax", type: "number", title: "với thời gian hoạt động tối đa theo phút", defaultValue:"1"
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