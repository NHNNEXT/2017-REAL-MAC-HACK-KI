//
//  Checkbox.m
//  REAL-AMIGO
//
//  Created by YongJai on 25/10/2017.
//  Copyright © 2017 YongJai. All rights reserved.
//

#import "Checkbox.h"

@implementation Checkbox{
    UILabel *label;
    BOOL textIsSet;
}

@synthesize text = _text;

- (id)initWithCoder:(NSCoder *)aDecoder{
    self = [super initWithCoder:aDecoder];
    if (self != nil) {
        [self initInternals];
    }
    return self;
}

- (id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self != nil) {
        [self initInternals];
    }
    return self;
}

- (void) initInternals{
    _boxFillColor = [UIColor whiteColor];
    _boxBorderColor = [UIColor colorWithRed:3.0f/255.0f green:193.0f/255.0f blue:111.0f/255.0f alpha:1];
    _checkColor = [UIColor colorWithRed:14.0f/255.0f green:96.0f/255.0f blue:61.0f/255.0f alpha:1];
    _isChecked = YES;
    _isEnabled = YES;
    _showTextLabel = NO;
    textIsSet = NO;
    self.backgroundColor = [UIColor clearColor];
}

-(CGSize)intrinsicContentSize{
    if (_showTextLabel) {
        return CGSizeMake(160, 40);
    }
    else{
        return CGSizeMake(40, 40);
    }
}


- (void)drawRect:(CGRect)rect {
    
    // Drawing code
    [_boxFillColor setFill];
    [_boxBorderColor setStroke];
    
    //User set flag to draw label
    if (_showTextLabel == YES) {
        //check if label has already been created... if not create a new label and set some basic styles
        if (!textIsSet) {
            label = [[UILabel alloc] initWithFrame:CGRectMake(self.frame.size.width/4 + 5, 0, self.frame.size.width*3/4 - 5, self.frame.size.height)];
            label.backgroundColor = [UIColor clearColor];
            [self addSubview:label];
            textIsSet = YES;
        }
        
        //style label
        label.font = _labelFont;
        label.textColor = _labelTextColor;
        label.text = self.text;
        
        //create enclosing box for checkbox
        UIBezierPath *boxPath = [UIBezierPath bezierPathWithRoundedRect:CGRectMake(2, 2, self.frame.size.width/4 - 4, self.frame.size.height - 4) cornerRadius:self.frame.size.width/20];
        boxPath.lineWidth = 4;
        [boxPath fill];
        [boxPath stroke];
        
        //if control is checked draw checkmark
        if (_isChecked == YES) {
            UIBezierPath *checkPath = [UIBezierPath bezierPath];
            checkPath.lineWidth = 5;
            [checkPath moveToPoint:CGPointMake(self.frame.size.width * 1/5, self.frame.size.height/5)];
            [checkPath addLineToPoint:CGPointMake(self.frame.size.width/8, self.frame.size.height * 4/5)];
            [checkPath addLineToPoint:CGPointMake(self.frame.size.width/20, self.frame.size.height/2)];
            [_checkColor setStroke];
            [checkPath stroke];
        }
    }
    
    //no text label in this scenario
    else{
        UIBezierPath *boxPath = [UIBezierPath bezierPathWithRoundedRect:CGRectMake(2, 2, self.frame.size.width - 4, self.frame.size.height - 4) cornerRadius:self.frame.size.width/5];
        boxPath.lineWidth = 4;
        [boxPath fill];
        [boxPath stroke];
        if (_isChecked == YES) {
            UIBezierPath *checkPath = [UIBezierPath bezierPath];
            checkPath.lineWidth = 5;
            [checkPath moveToPoint:CGPointMake(self.frame.size.width * 4/5, self.frame.size.height/5)];
            [checkPath addLineToPoint:CGPointMake(self.frame.size.width/2, self.frame.size.height * 4/5)];
            [checkPath addLineToPoint:CGPointMake(self.frame.size.width/5, self.frame.size.height/2)];
            [_checkColor setStroke];
            [checkPath stroke];
        }
    }
    
    //check if control is enabled...lower alpha if not and disable interaction
    if (_isEnabled == YES) {
        self.alpha = 1.0f;
        self.userInteractionEnabled = YES;
    } else {
        self.alpha = 0.6f;
        self.userInteractionEnabled = NO;
    }
    
    [self setNeedsDisplay];
    
}

-(BOOL)beginTrackingWithTouch:(UITouch *)touch withEvent:(UIEvent *)event{
    [self setChecked:!_isChecked];
    return true;
}

-(void)setChecked:(BOOL)isChecked{
    _isChecked = isChecked;
    [self setNeedsDisplay];
}
-(void)setEnabled:(BOOL)isEnabled{
    _isEnabled = isEnabled;
    [self setNeedsDisplay];
}
-(void)setText:(NSString *)stringValue{
    _text = stringValue;
    [self setNeedsDisplay];
}

@end
