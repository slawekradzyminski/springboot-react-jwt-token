import React, { ChangeEvent } from 'react'
import { Tab } from 'semantic-ui-react'
import UserTable from './UserTable'
import OrderTable from './OrderTable'
import { ApiUser } from '../../types/user'
import { Order } from '../../types/order'

interface AdminTabProps {
  isUsersLoading: boolean,
  users: ApiUser[],
  userUsernameSearch: string,
  isOrdersLoading: boolean,
  orderDescription: string,
  orders: Order[],
  orderTextSearch: string,
  handleInputChange: (event: ChangeEvent<HTMLInputElement>) => void,
  handleDeleteUser: (username: string) => void,
  handleSearchUser: () => void,
  handleCreateOrder: () => void,
  handleDeleteOrder: (isbn: string) => void, 
  handleSearchOrder: () => void
}

const AdminTab = (props: AdminTabProps) => {
  const panes = [
    {
      menuItem: { key: 'users', icon: 'users', content: 'Users' },
      render: () => (
        <Tab.Pane loading={props.isUsersLoading}>
          <UserTable
            users={props.users}
            userUsernameSearch={props.userUsernameSearch}
            handleInputChange={props.handleInputChange}
            handleDeleteUser={props.handleDeleteUser}
            handleSearchUser={props.handleSearchUser}
          />
        </Tab.Pane>
      )
    },
    {
      menuItem: { key: 'orders', icon: 'laptop', content: 'Orders' },
      render: () => (
        <Tab.Pane loading={props.isOrdersLoading}>
          <OrderTable
            orders={props.orders}
            orderDescription={props.orderDescription}
            orderTextSearch={props.orderTextSearch}
            handleInputChange={props.handleInputChange}
            handleCreateOrder={props.handleCreateOrder}
            handleDeleteOrder={props.handleDeleteOrder}
            handleSearchOrder={props.handleSearchOrder}
          />
        </Tab.Pane>
      )
    }
  ]

  return (
    <Tab menu={{ attached: 'top' }} panes={panes} />
  )
}

export default AdminTab