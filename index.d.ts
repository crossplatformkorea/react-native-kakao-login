interface callbackType {
  (err: string, result: string): void
}

/**
 * login
 * @param {callbackType} callback callback received from native module
 * @returns {void}
 */
export function login(callback: callbackType): void;

/**
 * logout
 * @param {callbackType} callback callback received from native module
 * @returns {void}
 */
export function logout(callback: callbackType): void;

/**
 * getProfile
 * @param {callbackType} callback callback received from native module. id, nickname, email, display_id, phone_number, email_verified, kakatalk_user, profile_image_path, has_signed_up values will be received with json string in result field.
 * @returns {void}
 */
export function getProfile(callback: callbackType): void;
