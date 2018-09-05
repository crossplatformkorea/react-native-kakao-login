export interface ICallback<T> {
  (error: Error | undefined, result: T | undefined): void
}

export interface ITokenInfo {
  token: string;
}

export interface IProfile {
  id: string;
  nickname: string;
  email: string | null;
  display_id: string | null;
  phone_number: string | null;
  email_verified: boolean;
  kakaotalk_user: boolean;
  profile_image_path: string | null;
  thumb_image_path: string | null;
  has_signed_up:  boolean;
}

/**
 * login
 * @param {callbackType} callback callback received from native module
 * @returns {void}
 */
export function login(callback: ICallback<ITokenInfo>): void;

/**
 * logout
 * @param {callbackType} callback callback received from native module
 * @returns {void}
 */
export function logout(callback: ICallback<string>): void;

/**
 * getProfile
 * @param {callbackType} callback callback received from native module. id, nickname, email, display_id, phone_number, email_verified, kakatalk_user, profile_image_path, has_signed_up values will be received with json string in result field.
 * @returns {void}
 */
export function getProfile(callback: ICallback<IProfile>): void;
