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
    * DataSetRequest.Builder.of("ZILL/N03346_A").build();
      * public static Builder of(final String quandlCode) {
          ArgumentChecker.notNull(quandlCode, "quandlCode");
          return new Builder(quandlCode);
        }
      * public DataSetRequest build() {
          return new DataSetRequest(this);
        }
    // DataSetRequest is holding "ZILL/N03346_A" ^^^
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
        //getClient() returns a Jersey client
      * protected Client getClient() {
          return ClientBuilder.newClient();
        }
      * WebTarget target = client.target(API_BASE_URL_V1); //Builds a new web resource target
      * private WebTarget withAuthToken(final WebTarget target) {
          if (_sessionOptions.getAuthToken() != null) {
            return target.queryParam(AUTH_TOKEN_PARAM_NAME, _sessionOptions.getAuthToken());
          } else {
            return target;
          }
        }
      * public WebTarget appendPathAndQueryParameters(final WebTarget webTarget) {
          ArgumentChecker.notNull(webTarget, "webTarget");
          WebTarget resultTarget = webTarget;
          resultTarget = resultTarget.path(DATASETS_RELATIVE_URL);
          resultTarget = resultTarget.path(_quandlCode + EXTENSION);
          if (_startDate != null) {
            resultTarget = resultTarget.queryParam(START_DATE_PARAM, _startDate.toString());
          }
          if (_endDate != null) {
            resultTarget = resultTarget.queryParam(END_DATE_PARAM, _endDate.toString());
          }
          if (_columnIndex != null) {
            resultTarget = resultTarget.queryParam(COLUMN_INDEX_PARAM, _columnIndex);
          }
          if (_frequency != null) {
            resultTarget = resultTarget.queryParam(FREQUENCY_PARAM, _frequency.getQuandlString());
          }
          if (_maxRows != null) {
            resultTarget = resultTarget.queryParam(MAX_ROWS_PARAM, _maxRows);
          }
          if (_transform != null) {
            resultTarget = resultTarget.queryParam(TRANSFORM_PARAM, _transform.getQuandlString());
          }
          if (_sortOrder != null) {
            resultTarget = resultTarget.queryParam(SORT_ORDER_PARAM, _sortOrder.getQuandlString());
          }
          return resultTarget;
        }
      * tabularResponse = _sessionOptions.getRESTDataProvider().getTabularResponse(target);
      * 