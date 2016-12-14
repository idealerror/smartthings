/**
 *  Insteon On/Off Switch
 *  Original Author     : ethomasii@gmail.com
 *  Creation Date       : 2013-12-08
 *
 *  Rewritten by        : idealerror
 *  Last Modified Date  : 2016-12-13 
 *  
 *  Disclaimer about 3rd party server:
 * 
 *  The refresh function of this device type currently 
 *  calls out to my 3rd party server to contact your hub
 *  to determine the status of your device.  The reason
 *  for this is SmartThings cannot parse the XML that 
 *  the hub gives us.  It's malformed.  If you're
 *  uncomfortable with my server contacting your hub 
 *  directly, you should not use this device type.
 *  I do not store or log any information related to 
 *  this device type.
 * 
 *  Changelog:
 * 
 *  2016-12-13: Added background refreshing every 3 minutes
 *  2016-11-21: Added refresh/polling functionality
 *  2016-10-15: Added full dimming functions
 *  2016-10-01: Redesigned interface tiles
 */
 
import groovy.json.JsonSlurper
 
preferences {
    input("deviceid", "text", title: "Device ID", description: "Your Insteon device.  Do not include periods example: FF1122.")
    input("host", "text", title: "URL", description: "The URL of your Hub (without http:// example: my.hub.com ")
    input("port", "text", title: "Port", description: "The hub port.")
    input("username", "text", title: "Username", description: "The hub username (found in app)")
    input("password", "text", title: "Password", description: "The hub password (found in app)")
} 
 
metadata {
    definition (name: "Insteon On/Off Switch or Plug", author: "idealerror", oauth: true) {
        capability "Polling"
        capability "Switch"
        capability "Refresh"
    }

    // simulator metadata
    simulator {
    }

    // UI tile definitions
    tiles(scale:2) {
       multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#79b821", nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
				attributeState "turningOn", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#79b821", nextState:"turningOff"
				attributeState "turningOff", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
			}

		}
        
        standardTile("refresh", "device.switch", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}

		main(["switch"])
		details(["switch", "refresh"])
    }
}

// Not in use
def parse(String description) {
}

def on() {
    log.debug "Turning device ON"
    sendCmd("11", "FF")
    sendEvent(name: "switch", value: "on");
    sendEvent(name: "level", value: 100, unit: "%")
}

def off() {
    log.debug "Turning device OFF"
    sendCmd("13", "00")
    sendEvent(name: "switch", value: "off");
    sendEvent(name: "level", value: 0, unit: "%")
}

def setLevel(value) {

	// log.debug "setLevel >> value: $value"
    
    // Max is 255
    def percent = value / 100
    def realval = percent * 255
	def valueaux = realval as Integer
	def level = Math.max(Math.min(valueaux, 255), 0)
	if (level > 0) {
		sendEvent(name: "switch", value: "on")
	} else {
		sendEvent(name: "switch", value: "off")
	}
    // log.debug "dimming value is $valueaux"
    log.debug "dimming to $level"
    dim(level,value)
}

def sendCmd(num, level)
{
    log.debug "Sending Command"

    httpGet("http://${settings.username}:${settings.password}@${settings.host}:${settings.port}//3?0262${settings.deviceid}0F${num}${level}=I=3") {response -> 
        def content = response.data
        
        // log.debug content
    }
    log.debug "Command Completed"
}

def refresh()
{
    log.debug "Refreshing.."
    poll()
}

def poll()
{
    log.debug "Polling.."
    getStatus()
    runIn(180, refresh)
}

def ping()
{
    log.debug "Pinging.."
    poll()
}

def initialize(){
    poll()
}

def getStatus() {

    // Unfortunately SmartThings is unable to parse the XML from Insteon.
    // This site is not logging anything, it's strictly a pass through.

    def params = [
        uri: "http://st.idealerror.com/?url=http://${settings.username}:${settings.password}@${settings.host}:${settings.port}/sx.xml?${settings.deviceid}=1900"
    ]

    try {
        httpPost(params) { resp ->

			def jsonSlurper = new JsonSlurper()
            def object = jsonSlurper.parseText("${resp.data}")

            log.debug "Percent: ${object.percent}"
            log.debug "Status: ${object.status}"
            
            if (object.percent > 0) {
                sendEvent(name: "switch", value: "on")
                sendEvent(name: "level", value: object.percent, unit: "%")
            } else {
                sendEvent(name: "switch", value: "off")
                sendEvent(name: "level", value: object.percent, unit: "%")
            }
        }
    } catch (e) {
        log.error "something went wrong: $e"
    }
    
    //log.debug content
}