import type {ActivityIndicatorProps, StyleProp, ViewStyle} from 'react-native';

import {ActivityIndicator} from 'react-native';
import type {FC} from 'react';
import {StyledButton} from '../Styles';

type Props = {
  style?: StyleProp<ViewStyle>;
  indicatorColor?: ActivityIndicatorProps['color'];
  indicatorSize?: ActivityIndicatorProps['size'];
};

const ButtonLoading: FC<Props> = ({
  style,
  indicatorColor,
  indicatorSize = 'small',
}) => {
  return (
    <StyledButton style={style}>
      {/* @ts-ignore */}
      <ActivityIndicator size={indicatorSize} color={indicatorColor} />
    </StyledButton>
  );
};

export default ButtonLoading;
