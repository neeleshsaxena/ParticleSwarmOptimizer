setwd("C:/Users/Neelu/Documents/Algo&DataStructures/FinalProject")
install.packages("data.table")
library(data.table)

data_customer <- fread(input ="Master_Address_List.csv",header = TRUE)

data_customer$id <- seq.int(nrow(data_customer))


install.packages("randomNames")
require(randomNames)

data_customer$name <- randomNames(331447, which.names="first")

randomNames()

write.csv(data_customer,file = "Master_data.csv",row.names = FALSE)
