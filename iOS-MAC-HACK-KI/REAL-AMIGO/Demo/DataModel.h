//
//  DataModel.h
//  REAL-AMIGO
//
//  Created by YongJai on 26/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DataModel : NSObject

@property (weak, nonatomic)NSString *date;

- (NSString *)getDate:(NSString *)dateData;

@end
