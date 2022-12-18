import React, { ChangeEvent } from 'react'
import { Form, Button, Input, Table } from 'semantic-ui-react'
import { ApiUser } from '../../types/user'

interface UserTableProps {
  users: ApiUser[],
  userUsernameSearch: string,
  handleInputChange: (event: ChangeEvent<HTMLInputElement>) => void,
  handleDeleteUser: (username: string) => void,
  handleSearchUser: () => void,
}

function UserTable(props: UserTableProps) {

  const getUserList = () => {
    if (props.users.length === 0) {
      return (
        <Table.Row key='no-user'>
          <Table.Cell collapsing textAlign='center' colSpan='6'>No user</Table.Cell>
        </Table.Row>
      )
    } else {
      return props.users.map(user => {
        return (
          <Table.Row key={user.id}>
            <Table.Cell collapsing>
              <Button
                circular
                color='red'
                size='small'
                icon='trash'
                disabled={user.username === 'admin'}
                onClick={() => props.handleDeleteUser(user.username)}
              />
            </Table.Cell>
            <Table.Cell>{user.id}</Table.Cell>
            <Table.Cell>{user.username}</Table.Cell>
            <Table.Cell>{user.name}</Table.Cell>
            <Table.Cell>{user.email}</Table.Cell>
            <Table.Cell>{user.role}</Table.Cell>
          </Table.Row>
        )
      })
    }
  }

  return (
    <>
      <Form onSubmit={props.handleSearchUser}>
        <Input
          action={{ icon: 'search' }}
          name='userUsernameSearch'
          placeholder='Search by Username'
          value={props.userUsernameSearch}
          onChange={props.handleInputChange}
        />
      </Form>
      <Table compact striped selectable>
        <Table.Header>
          <Table.Row>
            <Table.HeaderCell width={1} />
            <Table.HeaderCell width={1}>ID</Table.HeaderCell>
            <Table.HeaderCell width={3}>Username</Table.HeaderCell>
            <Table.HeaderCell width={4}>Name</Table.HeaderCell>
            <Table.HeaderCell width={5}>Email</Table.HeaderCell>
            <Table.HeaderCell width={2}>Role</Table.HeaderCell>
          </Table.Row>
        </Table.Header>
        <Table.Body>
          {getUserList()}
        </Table.Body>
      </Table>
    </>
  )
}

export default UserTable