import React, {useReducer} from 'react';

import {User} from '../types';
import createCtx from '../utils/createCtx';

interface Context {
  state: State;
  setUser: (user: User) => void;
  resetUser: () => void;
  callDefault: () => void;
}

const [useCtx, Provider] = createCtx<Context>();

export enum ActionType {
  ResetUser = 'reset-user',
  SetUser = 'set-user',
  CallDefault = 'call-default',
}

export interface State {
  user: User | null;
}

const initialState: State = {
  user: null,
};

interface SetUserAction {
  type: ActionType.SetUser;
  payload: User;
}

interface ResetUserAction {
  type: ActionType.ResetUser;
}

interface GetStateAction {
  type: ActionType.CallDefault;
}

type Action = SetUserAction | ResetUserAction | GetStateAction;

interface Props {
  children?: React.ReactElement;
}

type Reducer = (state: State, action: Action) => State;

const callDefault = (dispatch: React.Dispatch<GetStateAction>) => (): void => {
  dispatch({
    type: ActionType.CallDefault,
  });
};

const setUser = (dispatch: React.Dispatch<SetUserAction>) => (
  user: User,
): void => {
  dispatch({
    type: ActionType.SetUser,
    payload: user,
  });
};

const resetUser = (dispatch: React.Dispatch<ResetUserAction>) => (): void => {
  dispatch({
    type: ActionType.ResetUser,
  });
};

// eslint-disable-next-line default-param-last
const reducer: Reducer = (state = initialState, action) => {
  switch (action.type) {
    case 'reset-user':
      return initialState;
    case 'set-user':
      return {...state, user: action.payload};
    default:
      return state;
  }
};

function AppProvider(props: Props): React.ReactElement {
  const [state, dispatch] = useReducer<Reducer>(reducer, initialState);

  const actions = {
    setUser: setUser(dispatch),
    resetUser: resetUser(dispatch),
    callDefault: callDefault(dispatch),
  };

  return <Provider value={{state, ...actions}}>{props.children}</Provider>;
}

export {useCtx as useAppContext, AppProvider};
