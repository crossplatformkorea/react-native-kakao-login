import type {
  ImageSourcePropType,
  ImageStyle,
  StyleProp,
  TextStyle,
  TouchableOpacityProps,
  ViewStyle,
} from 'react-native';

import {Body3} from '../Typography';
import type {FC} from 'react';
import React from 'react';
import {StyledButton} from '../Styles';
import {TouchableOpacity} from 'react-native';
import styled from 'styled-components/native';

const StyledImage = styled.Image`
  width: 24px;
  height: 24px;
  position: absolute;
  left: 16px;
`;

const StyledText = styled(Body3)`
  color: ${({theme}) => theme.textContrast};
`;

type Props = {
  testID?: TouchableOpacityProps['testID'];
  activeOpacity?: TouchableOpacityProps['activeOpacity'];
  onPress?: TouchableOpacityProps['onPress'];
  imgLeftSrc?: ImageSourcePropType;
  imgLeftStyle?: StyleProp<ImageStyle>;
  style?: ViewStyle;
  textStyle?: TextStyle;
  text?: string;
};

const Button: FC<Props> = ({
  testID,
  activeOpacity = 0.7,
  onPress,
  imgLeftSrc,
  imgLeftStyle,
  style,
  textStyle,
  text = '',
}) => {
  return (
    <TouchableOpacity
      testID={testID}
      activeOpacity={activeOpacity}
      onPress={onPress}>
      <StyledButton style={style}>
        {imgLeftSrc ? (
          <StyledImage style={imgLeftStyle} source={imgLeftSrc} />
        ) : null}
        <StyledText style={textStyle}>{text}</StyledText>
      </StyledButton>
    </TouchableOpacity>
  );
};

export default Button;
