/* eslint-disable no-console */
const CACHE = 'network-or-cache';

// Open a cache and use `addAll()` with an array of assets to add all of them
// to the cache. Return a promise resolving when all the assets are added.
const precache = async () => {
  const cache = await caches.open(CACHE);

  await cache.addAll([
    // ? Precached resources.
  ]);
};

// Time limited network request. If the network fails or the response is not
// served before timeout, the promise is rejected.
const fromNetwork = async (request, timeout) => {
  const timeoutId = setTimeout(() => {
    throw new Error('Request timeout.');
  }, timeout);

  const response = await fetch(request);

  clearTimeout(timeoutId);

  return response;
};

// Open the cache where the assets were stored and search for the requested
// resource. Notice that in case of no matching, the promise still resolves
// but it does with `undefined` as value.
const fromCache = async (request) => {
  const cache = await caches.open(CACHE);
  const matching = await cache.match(request);

  if (matching === undefined) throw new Error('no-match');

  return matching;
};

// On install, cache some resource.
self.addEventListener('install', (evt) => {
  console.log('The service worker is being installed.');

  // Ask the service worker to keep installing until the returning promise
  // resolves.
  evt.waitUntil(precache());
});

// On fetch, use cache but update the entry with the latest contents
// from the server.
self.addEventListener('fetch', (evt) => {
  console.log('The service worker is serving the asset.');

  // Try network and if it fails, go for the cached copy.
  evt.respondWith(
    fromNetwork(evt.request, 4000).catch(() => fromCache(evt.request)),
  );
});
