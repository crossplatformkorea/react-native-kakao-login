import {ROOT_URL, sample} from '../sample';

import fetchMock from 'jest-fetch-mock';

describe('testing sample api', () => {
  beforeEach(() => {
    fetchMock.enableMocks();
    fetchMock.resetMocks();
  });

  it('calls google and returns data to me', () => {
    const mockedResult = JSON.stringify({data: '12345'});

    fetchMock.mockResponseOnce(mockedResult);

    return sample({zoyi: 'zoyi'}).then(async (res) => {
      const result = await res.text();

      expect(result).toEqual(mockedResult);

      expect(fetchMock.mock.calls.length).toEqual(1);
      expect(fetchMock.mock.calls[0][0]).toEqual(`${ROOT_URL}`);
    });
  });

  it('throws an error if object is undefined', () => {
    const onResponse = jest.fn();
    const onError = jest.fn();

    sample(undefined)
      .then(onResponse)
      .catch(onError)
      .then(() => {
        expect(onResponse).not.toHaveBeenCalled();
        expect(onError).toHaveBeenCalled();
      });
  });

  it('throws an error if error occurs', () => {
    fetchMock.mockRejectedValue(new Error('error'));

    // fetchMock.mockResponseOnce(() =>
    //   sample(null).then(() => Promise.reject(new Error())),
    // );
    const onResponse = jest.fn();
    const onError = jest.fn();

    sample({})
      .then(onResponse)
      .catch(onError)
      .then(() => {
        expect(onResponse).not.toHaveBeenCalled();
        expect(onError).toHaveBeenCalled();
      });
  });
});
