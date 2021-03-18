export const ROOT_URL = 'http://localhost:3000/api';

export const sample = async (
  body: Record<string, unknown> | undefined,
  signal?: AbortSignal | undefined,
): Promise<Response> => {
  if (!body) throw new Error('No request object');

  const fetchOption = {
    signal,
    method: 'POST',
    headers: new Headers({
      Accept: 'application/json',
      'Content-Type': 'application/json',
    }),
    body: JSON.stringify(body),
  };

  try {
    const res: Response = await fetch(`${ROOT_URL}`, fetchOption);

    return res;
  } catch (err) {
    throw new Error(err);
  }
};
