(ns clojurewerkz.elastisch.native.response)

;;
;; API
;;

(defn ok?
  [response]
  ;; TODO: can this be detected more precisely
  ;;       from native client responses? MK.
  true)

(defn not-found?
  [response]
  (false? (:exists response)))

(defn acknowledged?
  [response]
  (:acknowledged response))

(defn valid?
  "Returns true if a validation query response indicates valid query, false otherwise"
  [response]
  (:valid response))

(defn timed-out?
  [response]
  (:timed_out response))

(defn total-hits
  "Returns number of search hits from a response"
  [response]
  (get-in response [:hits :total]))

(defn count-from
  "Returns total number of search hits from a response"
  [response]
  (get response :count))

(defn any-hits?
  "Returns true if a response has any search hits, false otherwise"
  [response]
  (> (total-hits response) 0))

(def no-hits? (complement any-hits?))

(defn hits-from
  "Returns search hits from a response as a collection. To retrieve hits overview, get the :hits
   key from the response"
  [response]
  (get-in response [:hits :hits]))

(defn facets-from
  "Returns facets information (overview and actual facets) from a response as a map. Keys in the map depend on
   the facets query performed"
  [response]
  (get response :facets {}))

(defn ids-from
  "Returns search hit ids from a response"
  [response]
  (if (any-hits? response)
    (set (map :_id (hits-from response)))
    #{}))

(defn matches-from
  "Returns matches from a percolation response as a collection."
  [response]
  (get response :matches []))