//
//  Checkbox.h
//  REAL-AMIGO
//
//  Created by YongJai on 25/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface Checkbox : UIControl

-(void)setChecked:(BOOL)isChecked;
-(void)setEnabled:(BOOL)isEnabled;
-(void)setText:(NSString *)stringValue;

@property IBInspectable UIColor *checkColor;
@property IBInspectable UIColor *boxFillColor;
@property IBInspectable UIColor *boxBorderColor;
@property IBInspectable UIFont *labelFont;
@property IBInspectable UIColor *labelTextColor;

@property IBInspectable BOOL isEnabled;
@property IBInspectable BOOL isChecked;
@property IBInspectable BOOL showTextLabel;
@property (nonatomic, strong) IBInspectable  NSString *text;

@end
