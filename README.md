# appfueled-twilio-capacitor

A Capacitor plugin that integrates Twilio Voice SDK for making and receiving VoIP calls within Ionic apps. This plugin enables seamless voice functionality for Android and iOS, supporting call initiation, acceptance, and termination, along with user registration via Twilio access tokens.

## Install

```bash
npm install appfueled-twilio-capacitor
npx cap sync
```

## API

<docgen-index>

* [`registerIncomingCallConnection(...)`](#registerincomingcallconnection)
* [`registerOutgoingCallConnection(...)`](#registeroutgoingcallconnection)
* [`acceptIncomingCallConnection()`](#acceptincomingcallconnection)
* [`rejectIncomingCallConnection()`](#rejectincomingcallconnection)
* [`startOutgoingCallConnection(...)`](#startoutgoingcallconnection)
* [`terminateOutgoingCallConnection()`](#terminateoutgoingcallconnection)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### registerIncomingCallConnection(...)

```typescript
registerIncomingCallConnection(options: { token: string; }) => Promise<void>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ token: string; }</code> |

--------------------


### registerOutgoingCallConnection(...)

```typescript
registerOutgoingCallConnection(options: { token: string; }) => Promise<void>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ token: string; }</code> |

--------------------


### acceptIncomingCallConnection()

```typescript
acceptIncomingCallConnection() => Promise<void>
```

--------------------


### rejectIncomingCallConnection()

```typescript
rejectIncomingCallConnection() => Promise<void>
```

--------------------


### startOutgoingCallConnection(...)

```typescript
startOutgoingCallConnection(options: { phoneNumber: string; options: { [key: string]: any; }; }) => Promise<void>
```

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code>{ phoneNumber: string; options: { [key: string]: any; }; }</code> |

--------------------


### terminateOutgoingCallConnection()

```typescript
terminateOutgoingCallConnection() => Promise<void>
```

--------------------

</docgen-api>
