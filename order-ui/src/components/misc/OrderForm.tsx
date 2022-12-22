import React, { ChangeEvent } from 'react'
import { Form, Button, Icon } from 'semantic-ui-react'

interface OrderFormProps {
  orderDescription: string,
  handleInputChange: (event: ChangeEvent<HTMLInputElement>) => void,
  handleCreateOrder: () => void,
}

const OrderForm = (props: OrderFormProps) => {
  const createBtnDisabled = props.orderDescription.trim() === ''
  return (
    <Form onSubmit={props.handleCreateOrder}>
      <Form.Group>
        <Form.Input
          name='orderDescription'
          placeholder='Description *'
          value={props.orderDescription}
          onChange={props.handleInputChange}
        />
        <Button icon labelPosition='right' disabled={createBtnDisabled}>
          Create<Icon name='add' />
        </Button>
      </Form.Group>
    </Form>
  )
}

export default OrderForm