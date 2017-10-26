//
//  DataModel.m
//  REAL-AMIGO
//
//  Created by YongJai on 26/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "DataModel.h"

@implementation DataModel 

- (NSString *)getDate:(NSString *)dateData {
    NSLog(@"1111%@", dateData);
    NSLog(@"2222%@", _date);
    _date = dateData;
   
    return _date;
}

@end
