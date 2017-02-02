/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Developer API
 *
 *  Author: SmartThings
 */

import groovy.json.JsonBuilder

definition(
    name: "Developer API",
    namespace: "smartthings",
    author: "SmartThings",
    description: "SmartApp API for developers",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    oauth: [displayName: "SmartThings Developer API", displayLink: ""])

preferences {
    section("Allow Endpoint to Control These Things...") {
        input "switches", "capability.switch", title: "Which Switches?", multiple: true, required: false
        input "dimmers", "capability.switchLevel", title: "Which Dimmers?", multiple: true, required: false
        input "thermostats", "capability.thermostat", title: "Which Thermostats?", multiple: true, required: false
        input "motions", "capability.motionSensor", title: "Which Motions?", multiple: true, required: false
        input "accelerations", "capability.accelerationSensor", title: "Which Accelerations?", multiple: true, required: false
        input "contacts", "capability.contactSensor", title: "Which Contacts?", multiple: true, required: false
        input "illuminants", "capability.illuminanceMeasurement", title: "Which Illuminance Sensors?", multiple: true, required: false
        input "temperatures", "capability.temperatureMeasurement", title: "Which Temperatures?", multiple: true, required: false
        input "humidities", "capability.relativeHumidityMeasurement", title: "Which Humidities?", multiple: true, required: false
        input "presences", "capability.presenceSensor", title: "Which Presence?", multiple: true, required: false
        input "locks", "capability.lock", title: "Which Locks?", multiple: true, required: false
        input "batteries", "capability.battery", title: "Which Batteries?", multiple: true, required: false
        input "powers", "capability.powerMeter", title: "Power Meters", required:false, multiple: true
        input "energys", "capability.energyMeter", title: "Energy Meters", required:false, multiple: true
        input "dioxides", "capability.carbonDioxideMeasurement", title: "Co2 Measurement", required: false, multiple: true
        input "signals", "capability.signalStrength", title: "Signal Strength", required: false, multiple: true
        input "leaks", "capability.waterSensor", title: "Water Detection", required: false, multiple: true
        input "sounds", "capability.soundPressureLevel", title: "Sound Pressure", required: false, multiple: true
        input "colors", "capability.colorControl", title: "Color", required: false, multiple: true
        input "colorTemperatures", "capability.colorTemperature", title: "Color Temperature", required: false, multiple: true
    }
}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def initialize() {
    subscribe(switches, "switch", handleSwitchEvent)
    subscribe(dimmers, "level", handleSwitchLevelEvent)
    subscribe(motions, "motion", handleMotionEvent)
    subscribe(accelerations, "acceleration", handleAccelerationEvent)
    subscribe(contacts, "contact", handleContactEvent)
    subscribe(illuminants, "illuminance", handleIlluminanceEvent)
    subscribe(temperatures, "temperature", handleTemperatureEvent)
    subscribe(humidities, "humidity", handleHumidityEvent)
    subscribe(locks, "locks", handleDoorLockEvent)
    subscribe(batteries, "battery", handleBatteryEvent)
    subscribe(powers, "power", handlePowerEvent)
    subscribe(energys, "energy", handleEnergyEvent)
    subscribe(presences, "presence", handlePresenceEvent)
    subscribe(dioxides, "dioxide", handleDioxideEvent)
    subscribe(signals, "signal", handleSignalEvent)
    subscribe(leaks, "leak", handleLeakEvent)
    subscribe(sounds, "sound", handleSoundEvent)
    subscribe(colors, "color", handleColorEvent)
}

def handleIlluminanceEvent(evt) {
    logField(evt) { it.toString() }
}

def handleHumidityEvent(evt) {
    logField(evt) { it.toString() }
}

def handleTemperatureEvent(evt) {
    logField(evt) { it.toString() }
}

def handleThermCoolPoint(evt) {
    logField(evt) { it.toString() }
}

def handleContactEvent(evt) {
    logField(evt) { it == "open" ? "1" : "0" }
}

def handleAccelerationEvent(evt) {
    logField(evt) { it == "active" ? "1" : "0" }
}

def handleMotionEvent(evt) {
    logField(evt) { it == "active" ? "1" : "0" }
}

def handleSwitchEvent(evt) {
    logField(evt) { it.toString() }
}

def handleSwitchLevelEvent(evt) {
    logField(evt) { it.toString() }
}

def handleDoorLockEvent(evt) {
    logField(evt) {it == "locked" ? "locked" : "unlocked" }
}

def handleBatteryEvent(evt) {
    logField(evt) { it.toString() }
}

def handlePowerEvent(evt) {
    logField(evt) { it.toString() }
}

def handleEnergyEvent(evt) {
    logField(evt) { it.toString() }
}

def handlePresenceEvent(evt) {
    logField(evt) { it.toString() }
}

def handleDioxideEvent(evt) {
    logField(evt) { it.toString() }
}

def handleLeakEvent(evt) {
    logField(evt) { it.toString() }
}

def handleSignalEvent(evt) {
    logField(evt) { it.toString() }
}

def handleSoundEvent(evt) {
    logField(evt) { it.toString() }
}

def handleColorEvent(evt) {
    logField(evt) { it.toString() }
}

def handleColorTemperatureEvent(evt) {
    logField(evt) { it.toString() }
}

mappings {
    // location
    path("/locations") {
        action: [
            GET: "listLocation"
        ]
    }
    // modes
    path("/modes") {
        action: [
            GET: "listModes"
        ]
    }
    path("/modes/:id") {
        action: [
            get: "switchMode"
        ]
    }
    // hub
    path("/hubs") {
        action: [
            GET: "listHubs"
        ]
    }
    path("/hubs/:id") {
        action: [
            GET: "getHub"
        ]
    }
    // devices
   
    path("/devices") {
        action: [
            GET: "listDevices"
        ]
    }
    path("/devices/:id") {
        action: [
            GET: "listDevices",
        ]
    }
    path("/devices/:id/commands") {
        action: [
            GET: "listDeviceCommands"
        ]
    }
   
    
    path("/devices/:id/:command"){
        action: [
            GET: "sendDeviceCommand"   
        ]
    }
    
    path("/devices/:id/:command/:secondary"){
        action: [
            GET: "sendDeviceCommandSecondary"   
        ]
    }
    
    path("/devices/:id/events") {
        action: [
            GET: "listDeviceEvents"
        ]
    }
    // Routines
    path("/routines") {
        action: [
            GET: "listRoutines"
        ]
    }
    path("/routines/:id") {
        action: [
            GET: "listRoutines",
            POST: "executeRoutine"
        ]
    }
}

/****************************
* Location Methods
****************************/
def listLocation() {
    def result = [:]
    ["contactBookEnabled", "name", "temperatureScale", "zipCode"].each {
        result << [(it) : location."$it"]
    }
    result << ["latitude" : location.latitude as String]
    result << ["longitude" : location.longitude as String]
    result << ["timeZone" : location.timeZone?.getDisplayName()]
    result << ["currentMode" : getMode(location.currentMode)]

    // add hubs for this location to the result
    def hubs = []
    location.hubs?.each {
        hubs << getHub(it)
    }
    result << ["hubs" : hubs]
    log.debug "Returning LOCATION: $result"
    result
}

/****************************
* Hubs Methods
****************************/
def listHubs() {
    def id = params.id
    // if there is an id parameter, list only that hub. Otherwise list all hubs in location
    if(id) {
    def hub = location.hubs?.find{it.id == id}
          if(hub) {
              getHub(hub, true)
          } else {
              httpError(404, "hub not found")
          }
    } else {
        def result = []
        location.hubs?.each {
            result << getHub(it)
        }
        log.debug "Returning HUBS: $result"
        result
    }
}

private getHub(hub, explodedView = false) {
    def result = [:]
    //put the id and name into the result
    ["id", "name"].each {
        result << [(it) : hub."$it"]
    }

    // if we want detailed information about this hub
    if(explodedView) {
        ["firmwareVersionString", "localIP", "localSrvPortTCP", "zigbeeEui", "zigbeeId"].each {
            result << [(it) : hub."$it"]
        }
        result << ["type" : hub.type as String]
    }
    log.debug "Returning HUB: $result"
    result
}

/****************************
* Modes Methods
****************************/
def listModes() {
    def id = params.id
    // if there is an id parameter, list only that mode. Otherwise list all modes in location
    if(id) {
        def themode = location.modes?.find{it.id == id}
        if(themode) {
            getMode(themode, true)
        } else {
            httpError(404, "mode not found")
        }
    } else {
        def result = []
        location.modes?.each {
            result << getMode(it)
        }
        log.debug "Returning MODES: $result"
        result
    }
}

private getMode(mode, explodedView = false) {
    def result = [:]
    ["id", "name"].each {
        result << [(it) : mode."$it"]
    }

    if(explodedView) {
        ["locationId"].each {
            result << [(it) : mode."$it"]
        }
    }
    log.debug "Returning MODE: $result"
    result
}

def switchMode() {
    def id = params.id
    def mode = location.modes?.find{it.id == id}
    if(mode) {
        log.debug "Setting mode to $mode.name in location: $location.name"
        location.setMode(mode.name)
        render contentType: "text/html", status: 204, data: "No Content"
    } else {
        httpError(404, "mode not found")
    }
}

/****************************
* Routine Methods
****************************/
def listRoutines() {
    def id = params.id
    def results = []
    // if there is an id parameter, list only that routine. Otherwise list all routines in location
    if(id) {
        def routine = location.helloHome?.getPhrases().find{it.id == id}
        def myRoutine = [:]
        if(!routine) {
            httpError(404, "Routine not found")
        } else {
            getRoutine(routine)
        }
    } else {
        location.helloHome?.getPhrases().each { routine ->
            results << getRoutine(routine)
        }
        log.debug "Returning ROUTINES: $results"
        results
    }
}

private getRoutine(routine) {
    def result = [:]
    ["id", "label"].each {
        result << [(it) : routine."$it"]
    }
    log.debug "Returning ROUTINE: $result"
    result
}

def executeRoutine() {
    def id = params.id
    def routine = location.helloHome?.getPhrases().find{it.id == id}
    if(!routine) {
        httpError(404, "Routine not found")
    } else {
        log.debug "Executing Routine: $routine.label in location: $location.name"
        location.helloHome?.execute(routine.label)
        render contentType: "text/html", status: 204, data: "No Content"
    }
}

/****************************
* Device Methods
****************************/
def listDeviceTypes() {
    def results = []
    settings.each {
        results << it.key
    }
    log.debug "Returning TYPES: $results"
    results
}

def listDevices() {
    def id = params.id
    // if there is an id parameter, list only that device. Otherwise list all devices in location
    if(id) {
        def device = allDevices?.find{it.id == id}
        deviceItem(device, true)
    } else {
        def result = []
        result << allDevices.collect{deviceItem(it, false)}
        log.debug "Returning DEVICES: $result"
        result[0]
    }
}

private deviceItem(device, explodedView) {
    if (!device) return null
    def results = [:]
    ["id", "name", "displayName"].each {
        results << [(it) : device."$it"]
    }

    if(explodedView) {
        def attrsAndVals = [:]
        device.supportedAttributes?.each {
            attrsAndVals << [(it.name) : device.currentValue(it.name)]
        }

        results << ["attributes" : attrsAndVals]
    }
    log.debug "Returning DEVICE: $results"
    results
}

def listDeviceEvents() {
    def numEvents = 20
    def id = params.id
    def device = allDevices?.find{it.id == id}

    if (!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Retrieving last $numEvents events"
        def events = device.events(max: numEvents)
        def result = events.collect{item(device, it)}
        log.debug "Returnings EVENTS: $result"
        result
    }
}

private getAllDevices() {
    //contactSensors + presenceSensors + temperatureSensors + accelerationSensors + waterSensors + lightSensors + humiditySensors

     def dev_list =
         ([] + switches
        + dimmers
        + motions
        + accelerations
        + contacts
        + illuminants
        + temperatures
        + humidities
        + locks 
        + alarms
        + batteries
        + thermostats 
        + medias 
        + musics 
        + speeches 
        + colors 
        + valves
        + waters 
        + presences 
        + leaks)?.findAll()?.unique { it.id }
        
        return dev_list
}

private item(device, s) {
    device && s ? [device_id: device.id, 
                   label: device.displayName, 
                   name: s.name, value: s.value, 
                   date: s.date, stateChange: s.stateChange, 
                   eventSource: s.eventSource] : null
}

def listDeviceCommands() {
    def id = params.id
    def device = allDevices?.find{it.id == id}
    def result = []
    if(!device) {
        httpError(404, "Device not found")
    } else {
        device.supportedCommands?.each {
            result << ["command" : it.name, "params"  : [:]]
        }
    }
    log.debug "Returning COMMANDS: $result"
    result
}

def sendDeviceCommand() {
    def id = params.id
     def device = allDevices?.find{it.id == id}
    def command = params.command
    def secondary_command = params.level
    
    device."$command"()
    if(!command) {
        httpError(404, "Device not found")
    }
    if(!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Executing command: $command on device: $device.displayName"
    }
}

def sendDeviceCommandSecondary() {
    def id = params.id
    def device = allDevices?.find{it.id == id}
    def command = params.command
    def secondary = params.secondary.toInteger()
    
    device."$command"(secondary)
    if(!command) {
        httpError(404, "Device not found")
    }
    if(!device) {
        httpError(404, "Device not found")
    } else {
        log.debug "Executing with secondary command: $command $secondary on device: $device.displayName"
    }
}

// Callback to some url 
private logField(evt, Closure c) {
    log.debug "The souce of this event is ${evt.source} and it was ${evt.id}"

    httpPostJson(uri: "#####SEND EVENTS TO YOUR ENDPOINT######",   body:[source: "smart_things", device: evt.deviceId, eventType: evt.name, value: evt.value, event_date: evt.isoDate, units: evt.unit, event_source: evt.source, state_changed: evt.isStateChange()]) {
        log.debug evt.name+" Event data successfully posted"
    }
}
