import '@expo/match-media';

import {useMediaQuery} from 'react-responsive';

export type MediaQueryType = {
  tablet: boolean;
  desktop: boolean;
};

export const useMedia = (): MediaQueryType => {
  const isTablet = useMediaQuery({minWidth: 768});
  const isDesktop = useMediaQuery({minWidth: 1224});

  return {
    tablet: isTablet,
    desktop: isDesktop,
  };
};
