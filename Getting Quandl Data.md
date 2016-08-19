Getting Quandl Data

  * QuandlSession session = QuandlSession.create(quandlApi);
    * public static QuandlSession create(final String authToken) {
        ArgumentChecker.notNull(authToken, "authToken");
          return new QuandlSession(SessionOptions.Builder.withAuthToken(authToken).build());
        }
      * public static Builder withAuthToken(final String authToken) {
          ArgumentChecker.notNull(authToken, "authToken");
          return new Builder(authToken);
        }
      * public SessionOptions build() {
          return new SessionOptions(this);
        }
  * TabularResult tabularResult = session.getDataSet(DataSetRequest.Builder.of("ZILL/N03346_A").build());
    * public TabularResult getDataSet(final DataSetRequest request) {
        ArgumentChecker.notNull(request, "request");

        Client client = getClient();
        WebTarget target = client.target(API_BASE_URL_V1);
        target = withAuthToken(target);
        target = request.appendPathAndQueryParameters(target);
        TabularResult tabularResponse = null;
        int retries = 0;
        do {
          try {
            tabularResponse = _sessionOptions.getRESTDataProvider().getTabularResponse(target);
          } catch (QuandlTooManyRequestsException qtmre) {
            s_logger.debug("Quandl returned Too Many Requests, retrying if appropriate");
            if (qtmre.isDataExhausted()) {
              throw new QuandlRequestFailedException("Data request limit exceeded", qtmre);
            }
          } catch (QuandlServiceUnavailableException qsue) {
            s_logger.debug("Quandl returned Service Not Available, retrying if appropriate");        
          }
          // note checkRetries always returns true or throws an exception so we won't get tabularReponse == null
        } while (tabularResponse == null && _sessionOptions.getRetryPolicy().checkRetries(retries++));

        return tabularResponse;
      }