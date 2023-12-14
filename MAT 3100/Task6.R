# Read in my cleaned hurricane data
hurrData = read.csv("C:\\Users\\epicd\\Documents\\R Project\\Hurricane Data - Clean.csv",header=TRUE)
# Filter out all entries that aren't Tropical Storms or Hurricanes
hurrData <- subset(hurrData, Status == "TS" | Status == "HU")
# Get the unique storm names
stormNames = unique(hurrData$Name)
# Get the number of unique storms
numStorms = length(unique(hurrData$Name))
# Initialize two lists of empty strings
start = rep("", numStorms)
end = rep("", numStorms)
# For each storm, get the first timestamp and the last one into the lists above
for(i in 1:numStorms){
	subset <- subset(hurrData, Name == stormNames[i])
	numSamples = length(subset[,1])
	start[i] = subset$Timestamp[1]
	end[i] = subset$Timestamp[numSamples]
}
# Create a data frame from the names, start timestamps, and end timestamps
timeFrame = data.frame(stormNames, start, end)
# Read in my cleaned buoy data
buoyData = read.csv("C:\\Users\\epicd\\Documents\\R Project\\Buoy Data - Clean.csv",header=TRUE)
# Filter out entries that have censored data
buoyData <- subset(buoyData, WSPD < 99 & BAR < 9999 & ATMP < 999 & WTMP < 999)
# Get the number of entries in the buoy data
m = length(buoyData$YYYY)
# Create a data frame that only consists of a list of zeroes
during <- data.frame(During = rep(0, m))
# For each entry in the buoy data, determine if it is during a storm or not based on the timeFrame data frame
for(i in 1:m){
	if(buoyData[i,]$Timestamp..Hours. < timeFrame$start[1] | buoyData[i,]$Timestamp..Hours. > timeFrame$end[numStorms]){
		next
	}
	for(j in 1:numStorms){
		if(buoyData[i,]$Timestamp..Hours. >= timeFrame$start[j] & buoyData[i,]$Timestamp..Hours. <= timeFrame$end[j]){
			during$During[i] = 1
			break
		}
	}
}
# Append the during data frame horizontally onto the buoyData frame
buoyData <- cbind(buoyData, during)
# Summary not during storm
summary(subset(buoyData, During == 0))
# Summary during storm
summary(subset(buoyData, During == 1))
# Multiple linear regression model and summary
model = lm(During ~ WSPD + BAR + ATMP + WTMP, data = buoyData)
summary(model)

model = lm(During ~ WSPD + BAR + WTMP, data = buoyData)
summary(model)

# Each variable models
WSPDmodel = lm(During ~ WSPD, data = buoyData)
summary(WSPDmodel)
BARmodel = lm(During ~ BAR, data = buoyData)
summary(BARmodel)
ATMPmodel = lm(During ~ ATMP, data = buoyData)
summary(ATMPmodel)
WTMPmodel = lm(During ~ WTMP, data = buoyData)
summary(WTMPmodel)

# Each combination of 2 variables
# Read in my cleaned hurricane data
hurrData = read.csv("C:\\Users\\epicd\\Documents\\R Project\\Hurricane Data - Clean.csv",header=TRUE)
# Filter out all entries that aren't Tropical Storms or Hurricanes
hurrData <- subset(hurrData, Status == "TS" | Status == "HU")
# Get the unique storm names
stormNames = unique(hurrData$Name)
# Get the number of unique storms
numStorms = length(unique(hurrData$Name))
# Initialize two lists of empty strings
start = rep("", numStorms)
end = rep("", numStorms)
# For each storm, get the first timestamp and the last one into the lists above
for(i in 1:numStorms){
	subset <- subset(hurrData, Name == stormNames[i])
	numSamples = length(subset[,1])
	start[i] = subset$Timestamp[1]
	end[i] = subset$Timestamp[numSamples]
}
# Create a data frame from the names, start timestamps, and end timestamps
timeFrame = data.frame(stormNames, start, end)
# Read in my cleaned buoy data
buoyData = read.csv("C:\\Users\\epicd\\Documents\\R Project\\Buoy Data - Clean.csv",header=TRUE)
# Filter out entries that have censored data
buoyData <- subset(buoyData, WSPD < 99 & BAR < 9999 & ATMP < 999 & WTMP < 999)
# Get the number of entries in the buoy data
m = length(buoyData$YYYY)
# Create a data frame that only consists of a list of zeroes
during <- data.frame(During = rep(0, m))
# For each entry in the buoy data, determine if it is during a storm or not based on the timeFrame data frame
for(i in 1:m){
	if(buoyData[i,]$Timestamp..Hours. < timeFrame$start[1] | buoyData[i,]$Timestamp..Hours. > timeFrame$end[numStorms]){
		next
	}
	for(j in 1:numStorms){
		if(buoyData[i,]$Timestamp..Hours. >= timeFrame$start[j] & buoyData[i,]$Timestamp..Hours. <= timeFrame$end[j]){
			during$During[i] = 1
			break
		}
	}
}
# Append the during data frame horizontally onto the buoyData frame
buoyData <- cbind(buoyData, during)
# Summary not during storm
summary(subset(buoyData, During == 0))
# Summary during storm
summary(subset(buoyData, During == 1))
# Multiple linear regression model and summary
model = lm(During ~ WSPD + BAR + ATMP + WTMP, data = buoyData)
summary(model)

# Each variable models
model = lm(During ~ WSPD, data = buoyData)
summary(model)
model = lm(During ~ BAR, data = buoyData)
summary(model)
model = lm(During ~ WTMP, data = buoyData)
summary(model)
model = lm(During ~ ATMP, data = buoyData)
summary(model)

# Each combination of 2 variables
model = lm(During ~ WSPD + BAR, data = buoyData)
summary(model)
model = lm(During ~ WSPD + ATMP, data = buoyData)
summary(model)
model = lm(During ~ WSPD + WTMP, data = buoyData)
summary(model)
model = lm(During ~ BAR + ATMP, data = buoyData)
summary(model)
model = lm(During ~ BAR + WTMP, data = buoyData)
summary(model)
model = lm(During ~ WTMP + ATMP, data = buoyData)
summary(model)

# Each combination of 3 variables
model = lm(During ~ WSPD + BAR + WTMP, data = buoyData)
summary(model)
model = lm(During ~ WSPD + BAR + ATMP, data = buoyData)
summary(model)
model = lm(During ~ WSPD + ATMP + WTMP, data = buoyData)
summary(model)
model = lm(During ~ BAR + WTMP + ATMP, data = buoyData)
summary(model)